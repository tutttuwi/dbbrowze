package me.tutttuwi.dbbrowze.controller.api.resource;

import java.util.List;

import me.tutttuwi.dbbrowze.entity.Dto;

public interface Resource {

  List<? extends Dto> getData();

  void setData(List<? extends Dto> data);

  String getMessage();

  void setMessage(String message);
}
