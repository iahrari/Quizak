package ir.dagger.quizak.controller.command.converters

import org.springframework.core.convert.converter.Converter

interface KConverter<out S, out T>
    : Converter<@UnsafeVariance S, @UnsafeVariance T>