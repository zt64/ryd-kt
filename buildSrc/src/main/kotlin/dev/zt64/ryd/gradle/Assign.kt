package dev.zt64.ryd.gradle

import org.gradle.api.provider.Property

fun <T> Property<T>.assign(value: T) = set(value)