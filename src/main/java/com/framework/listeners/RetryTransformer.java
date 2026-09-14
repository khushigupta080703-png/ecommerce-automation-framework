package com.framework.listeners;

import com.framework.utils.RetryAnalyzer;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Applies RetryAnalyzer to every @Test method automatically, so
 * individual test classes don't need retryAnalyzer = RetryAnalyzer.class
 * repeated on every method.
 */
public class RetryTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                           Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
