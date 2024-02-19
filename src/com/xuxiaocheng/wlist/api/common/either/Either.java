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

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A class that acts as a container for a value of one of two types.
 * An Either can be either be a "Left", containing an LHS value or a "Right" containing an RHS value,
 * but it cannot be "neither" nor "both".
 * @param <L> the type of the LHS value
 * @param <R> the type of the RHS value
 */
public abstract sealed class Either<L, R> permits Left, Right {
    /**
     * Returns a Left containing the given non-{@code null} LHS value.
     * @param value the LHS value.
     * @param <L> the type of the LHS value.
     * @param <R> an arbitrary RHS type.
     * @return a Left containing the LHS value.
     * @throws NullPointerException if value is {@code null}.
     */
    public static <L, R> Either<L, R> left(final L value) {
        return new Left<>(value);
    }

    /**
     * Returns a Right containing the given non-{@code null} RHS value.
     * @param value the RHS value.
     * @param <L> an arbitrary LHS type.
     * @param <R> the type of the RHS value.
     * @return a Right containing the RHS value.
     * @throws NullPointerException if value is {@code null}.
     */
    public static <L, R> Either<L, R> right(final R value) {
        return new Right<>(value);
    }

    /**
     * Returns {@code true} if this is a Left, otherwise {@code false}.
     * @return {@code true} if this is a Left, otherwise {@code false}.
     */
    public abstract boolean isLeft();

    /**
     * Returns {@code true} if this is a Right, otherwise {@code false}.
     * @return {@code true} if this is a Right, otherwise {@code false}.
     */
    public abstract boolean isRight();

    /**
     * If this is a Left, return an {@code Optional} containing the LHS value.
     * Otherwise, return an empty {@code Optional}.
     * @return the LHS value if this is a Left, otherwise an empty {@code Optional}.
     */
    public abstract Optional<L> getLeft();

    /**
     * If this is a Right, returns an {@code Optional} containing the RHS value.
     * Otherwise, returns an empty {@code Optional}.
     * @return the RHS value if this is a Right, otherwise an empty {@code Optional}.
     */
    public abstract Optional<R> getRight();

    /**
     * If this is a Left, return the result of applying the {@code leftMapper} to the LHS value.
     * Otherwise, return the result of applying the {@code rightMapper} to the RHS value.
     * @param leftMapper the function to apply if this is a Left.
     * @param rightMapper the function to apply if this is a Right.
     * @param <U> the result type of both {@code leftMapper} and {@code rightMapper}.
     * @return the result of applying either {@code leftMapper} or {@code rightMapper}.
     */
    public abstract <U> U fold(final Function<? super L, ? extends U> leftMapper, final Function<? super R, ? extends U> rightMapper);

    /**
     * If this is a Left, return a Left containing the result of applying the mapper function to the LHS value.
     * Otherwise, return a Right containing the RHS value.
     * @param mapper the function to apply to the LHS value.
     * @param <L2> the new LHS type.
     * @return an equivalent instance if this is a Right, otherwise a Left containing
     *         the result of applying {@code mapper} to the LHS value.
     * @throws NullPointerException if the {@code mapper} returns a {@code null} result.
     */
    public abstract <L2> Either<L2, R> mapLeft(final Function<? super L, ? extends L2> mapper);

    /**
     * If this is a Right, return a Right containing the result of applying the mapper function to the RHS value.
     * Otherwise, return a Left containing the LHS value.
     * @param mapper the function to apply to the RHS value, if this is a Right.
     * @param <R2> the new RHS type.
     * @return an equivalent instance if this is a Left, otherwise a Right containing
     *         the result of applying {@code mapper} to the RHS value.
     * @throws NullPointerException if the {@code mapper} returns a {@code null} result.
     */
    public abstract <R2> Either<L, R2> mapRight(final Function<? super R, ? extends R2> mapper);

    /**
     * If this is a Left, return the result of applying the mapper function to the LHS value.
     * Otherwise, return a Right containing the RHS value.
     * @param mapper a mapper function.
     * @param <L2> the new LHS type.
     * @return an equivalent instance if this is a Right, otherwise the result of
     *         applying {@code mapper} to the LHS value.
     */
    public abstract <L2> Either<L2, R> flatMapLeft(final Function<? super L, ? extends Either<? extends L2, ? extends R>> mapper);

    /**
     * If this is a Right, return the result of applying the mapper function to the RHS value.
     * Otherwise, return a Left containing the LHS value.
     * @param mapper a mapper function.
     * @param <R2> the new RHS type.
     * @return an equivalent instance if this is a Left, otherwise the result of
     *         applying {@code mapper} to the RHS value.
     */
    public abstract <R2> Either<L, R2> flatMapRight(final Function<? super R, ? extends Either<? extends L, ? extends R2>> mapper);

    /**
     * If this is a Left, apply the predicate function to the LHS value.
     * If this is a Right, return a Right containing the RHS value.
     * If the predicate function returns an empty result, return a Left containing the LHS value.
     * If the result is not empty, return a Right containing the result.
     * @param predicate a function that acts as a filter predicate.
     * @return filter result.
     */
    public abstract Either<L, R> filterLeft(final Function<? super L, Optional<? extends R>> predicate);

    /**
     * If this is a Right, apply the predicate function to the RHS value.
     * If this is a Left, return a Left containing the LHS value.
     * If the predicate function returns an empty result, return a Right containing the RHS value.
     * If the result is not empty, return a Left containing the result.
     * @param predicate a function that acts as a filter predicate.
     * @return filter result.
     */
    public abstract Either<L, R> filterRight(final Function<? super R, Optional<? extends L>> predicate);

    /**
     * If this is a Left, perform the {@code leftAction} with the LHS value.
     * Otherwise, perform the {@code rightAction} with the RHS value.
     * @param leftAction action to run if this is a Left.
     * @param rightAction action to run if this is a Right.
     */
    public abstract void action(final Consumer<? super L> leftAction, final Consumer<? super R> rightAction);

    /**
     * If this is a Left, perform the {@code action} with the LHS value.
     * Otherwise, do nothing.
     * @param action action to run if this is a Left.
     * @return this.
     */
    public abstract Either<L, R> ifLeft(final Consumer<? super L> action);

    /**
     * If this is a Left, perform the {@code action} with the RHS value.
     * Otherwise, do nothing.
     * @param action action to run if this is a Right.
     * @return this.
     */
    public abstract Either<L, R> ifRight(final Consumer<? super R> action);

    /**
     * If this is a Left, return the LHS value.
     * Otherwise, return the LHS result of applying the mapper function to the RHS value.
     * @param mapper a mapper function.
     * @return the LHS value if this is a Left,
     *         or the result of applying the mapper function to the RHS value if this is a Right.
     */
    public abstract L leftOrElse(final Function<? super R, ? extends L> mapper);

    /**
     * If this is a Right, return the RHS value.
     * Otherwise, return the RHS result of applying the mapper function to the LHS value.
     * @param mapper a mapper function.
     * @return the RHS value if this is a Right,
     *         or the result of applying the mapper function to the LHS value if this is a Left.
     */
    public abstract R rightOrElse(final Function<? super L, ? extends R> mapper);

    /**
     * If this is a Left, return the LHS value.
     * Otherwise, throw an exception produced by the exception supplying function.
     * @param exceptionSupplier exception supplying function.
     * @param <T> type of the exception.
     * @return the LHS value if this is a Left.
     * @throws T the result of applying {@code exceptionSupplier} to the RHS value, if this is a Right.
     */
    public abstract <T extends Throwable> L leftOrThrow(final Function<? super R, ? extends T> exceptionSupplier) throws T;

    /**
     * If this is a Right, return the RHS value.
     * Otherwise, throw an exception produced by the exception supplying function.
     * @param exceptionSupplier exception supplying function.
     * @param <T> type of the exception.
     * @return the RHS value if this is a Right.
     * @throws T the result of applying {@code exceptionSupplier} to the LHS value, if this is a Left
     */
    public abstract <T extends Throwable> R rightOrThrow(final Function<? super L, ? extends T> exceptionSupplier) throws T;

    /**
     * Returns a string representation of this {@code Either}.
     * @return the string representation of this instance
     */
    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(final Object o);

    @Override
    public abstract int hashCode();
}
