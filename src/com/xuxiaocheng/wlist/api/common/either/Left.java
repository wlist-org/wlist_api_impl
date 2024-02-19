/*
MIT License

Copyright (c) 2021 jbock-java
Copyright (c) 2024 xuxiaocheng

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.xuxiaocheng.wlist.api.common.either;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Internal implementation of a Left-Either.
 * @param <L> the type of the LHS value.
 * @param <R> the type of the RHS value.
 */
final class Left<L, R> extends Either<L, R> {
    private final L value;

    Left(final L value) {
        super();
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public Optional<L> getLeft() {
        return Optional.of(this.value);
    }

    @Override
    public Optional<R> getRight() {
        return Optional.empty();
    }

    @Override
    public <U> U fold(final Function<? super L, ? extends U> leftMapper, final Function<? super R, ? extends U> rightMapper) {
        return leftMapper.apply(this.value);
    }

    @Override
    public <L2> Either<L2, R> mapLeft(final Function<? super L, ? extends L2> mapper) {
        return Either.left(mapper.apply(this.value));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R2> Either<L, R2> mapRight(final Function<? super R, ? extends R2> mapper) {
        return (Either<L, R2>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <L2> Either<L2, R> flatMapLeft(final Function<? super L, ? extends Either<? extends L2, ? extends R>> mapper) {
        return (Either<L2, R>) mapper.apply(this.value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R2> Either<L, R2> flatMapRight(final Function<? super R, ? extends Either<? extends L, ? extends R2>> mapper) {
        return (Either<L, R2>) this;
    }

    @Override
    public Either<L, R> filterLeft(final Function<? super L, Optional<? extends R>> predicate) {
        return predicate.apply(this.value).<Either<L, R>>map(Either::right).orElse(this);
    }

    @Override
    public Either<L, R> filterRight(final Function<? super R, Optional<? extends L>> predicate) {
        return this;
    }

    @Override
    public void action(final Consumer<? super L> leftAction, final Consumer<? super R> rightAction) {
        leftAction.accept(this.value);
    }

    @Override
    public Either<L, R> ifLeft(final Consumer<? super L> action) {
        action.accept(this.value);
        return this;
    }

    @Override
    public Either<L, R> ifRight(final Consumer<? super R> action) {
        return this;
    }

    @Override
    public L leftOrElse(final Function<? super R, ? extends L> mapper) {
        return this.value;
    }

    @Override
    public R rightOrElse(final Function<? super L, ? extends R> mapper) {
        return mapper.apply(this.value);
    }

    @Override
    public <T extends Throwable> L leftOrThrow(final Function<? super R, ? extends T> exceptionSupplier) throws T {
        return this.value;
    }

    @Override
    public <T extends Throwable> R rightOrThrow(final Function<? super L, ? extends T> exceptionSupplier) throws T {
        throw exceptionSupplier.apply(this.value);
    }

    @Override
    public String toString() {
        return String.format("Left[%s]", this.value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Left<?, ?> left)) return false;
        return Objects.equals(this.value, left.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }
}
