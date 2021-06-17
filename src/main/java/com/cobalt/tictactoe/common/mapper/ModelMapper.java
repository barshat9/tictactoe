package com.cobalt.tictactoe.common.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ModelMapper<S, T> {

  private final Function<S, T> toTarget;

  private final Function<T, S> toSource;

  public final T mapToTarget(S s) {
    return toTarget.apply(s);
  }

  public final S mapToSource(T t) {
    return toSource.apply(t);
  }

  public final List<T> mapToTarget(List<S> s) {
    return s.stream().map(this::mapToTarget).collect(Collectors.toList());
  }

  public final List<S> mapToSource(List<T> t) {
    return t.stream().map(this::mapToSource).collect(Collectors.toList());
  }
}
