package me.tutttuwi.dbbrowze.controller.api;

import java.util.ArrayList;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.dbbrowze.constant.MsgConst;
import me.tutttuwi.dbbrowze.controller.api.resource.ErrorResourceImpl;
import me.tutttuwi.dbbrowze.controller.api.resource.FieldErrorResource;
import me.tutttuwi.dbbrowze.exception.NoDataFoundException;
import me.tutttuwi.dbbrowze.exception.ValidationErrorException;
import me.tutttuwi.dbbrowze.util.MessageUtils;

/**
 * API用の例外ハンドラー.
 *
 * @author Tomo
 *
 */
@RestControllerAdvice(annotations = RestController.class) // HTMLコントローラーの例外を除外する
@Slf4j
public class AppErrorApiController extends ResponseEntityExceptionHandler {

  /**
   * 入力チェックエラーのハンドリング.
   *
   * @param ex exeption
   * @param request request
   * @return
   */
  @ExceptionHandler(ValidationErrorException.class)
  public ResponseEntity<Object> handleValidationErrorException(Exception ex, WebRequest request) {

    val fieldErrorContexts = new ArrayList<FieldErrorResource>();

    if (ex instanceof ValidationErrorException) {
      val vee = (ValidationErrorException) ex;

      vee.getErrors().ifPresent(errors -> {
        val fieldErrors = errors.getFieldErrors();

        if (fieldErrors != null) {
          fieldErrors.forEach(fieldError -> {
            val fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setFieldName(fieldError.getField());
            fieldErrorResource.setErrorType(fieldError.getCode());
            fieldErrorResource.setErrorMessage(fieldError.getDefaultMessage());
            fieldErrorContexts.add(fieldErrorResource);
          });
        }
      });
    }

    val locale = request.getLocale();
    val message =
        MessageUtils.getMsg(MsgConst.VALIDATION_ERROR.KEY, null, "validation error", locale);
    val errorContext = new ErrorResourceImpl();
    errorContext.setMessage(message);
    errorContext.setFieldErrors(fieldErrorContexts);

    val headers = new HttpHeaders();
    val status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(errorContext, headers, status);
  }

  /**
   * データ不存在エラーのハンドリング.
   *
   * @param ex exception
   * @param request request
   * @return
   */
  @ExceptionHandler(NoDataFoundException.class)
  public ResponseEntity<Object> handleNoDataFoundException(Exception ex, WebRequest request) {

    String parameterDump = this.dumpParameterMap(request.getParameterMap());
    log.info("no data found. dump: {}", parameterDump);

    val message = MessageUtils.getMsg(MsgConst.ERROR_DATA_NOT_FOUND.KEY, null, "no data found",
        request.getLocale());
    val errorResource = new ErrorResourceImpl();
    errorResource.setRequestId(String.valueOf(MDC.get("X-Track-Id")));
    errorResource.setMessage(message);
    errorResource.setFieldErrors(new ArrayList<>());

    val headers = new HttpHeaders();
    val status = HttpStatus.OK;
    return new ResponseEntity<>(errorResource, headers, status);
  }

  /**
   * 予期せぬ例外のハンドリング.
   *
   * @param ex exception
   * @param request request
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleUnexpectedException(Exception ex, WebRequest request) {
    return handleExceptionInternal(ex, null, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    String parameterDump = this.dumpParameterMap(request.getParameterMap());
    log.error(String.format("unexpected error has occurred. dump: %s", parameterDump), ex);

    val locale = request.getLocale();
    val message =
        MessageUtils.getMsg(MsgConst.ERROR_UNEXPECTED.KEY, null, "unexpected error", locale);
    val errorResource = new ErrorResourceImpl();
    errorResource.setRequestId(String.valueOf(MDC.get("X-Track-Id")));
    errorResource.setMessage(message);

    if (errorResource.getFieldErrors() == null) {
      errorResource.setFieldErrors(new ArrayList<>());
    }

    return new ResponseEntity<>(errorResource, headers, status);
  }

  /**
   * パラメータをダンプする.
   *
   * @param parameterMap parametersMap
   * @return
   */
  protected String dumpParameterMap(Map<String, String[]> parameterMap) {
    StringBuilder sb = new StringBuilder(256);
    parameterMap.forEach((key, values) -> {
      sb.append(key).append("=").append("[");
      for (String value : values) {
        sb.append(value).append(",");
      }
      sb.delete(sb.length() - 1, sb.length()).append("], ");
    });
    int length = sb.length();
    if (2 <= length) {
      sb.delete(length - 2, length);
    }

    return sb.toString();
  }
}
