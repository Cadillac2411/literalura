package com.literalura.demo.model;

public interface IConverterData {
    <T> T obterData(String json, Class<T> classe);
}
