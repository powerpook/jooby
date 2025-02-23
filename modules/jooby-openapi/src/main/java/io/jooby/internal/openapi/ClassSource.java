/**
 * Jooby https://jooby.io
 * Apache License Version 2.0 https://jooby.io/LICENSE.txt
 * Copyright 2014 Edgar Espina
 */
package io.jooby.internal.openapi;

import io.jooby.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassSource {
  private final ClassLoader classLoader;

  public ClassSource(ClassLoader loader) {
    this.classLoader = loader;
  }

  public ClassLoader getClassLoader() {
    return classLoader;
  }

  public byte[] loadClass(String classname) {
    try (InputStream stream = classLoader.getResourceAsStream(classname.replace(".", "/") + ".class")) {
      if (stream == null) {
        throw new ClassNotFoundException(classname);
      }
      return IOUtils.toByteArray(stream);
    } catch (Exception x) {
      throw SneakyThrows.propagate(x);
    }
  }

  public byte[] loadResource(String path) throws IOException {
    try (InputStream stream = classLoader.getResourceAsStream(path)) {
      if (stream == null) {
        throw new FileNotFoundException(path);
      }
      return IOUtils.toByteArray(stream);
    }
  }
}
