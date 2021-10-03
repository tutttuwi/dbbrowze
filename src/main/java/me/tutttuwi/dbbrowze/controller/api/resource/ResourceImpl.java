package me.tutttuwi.dbbrowze.controller.api.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import me.tutttuwi.dbbrowze.entity.Dto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceImpl implements Resource {

  List<? extends Dto> data;

  // メッセージ
  String message;
}
