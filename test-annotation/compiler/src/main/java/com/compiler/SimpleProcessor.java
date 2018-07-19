package com.compiler;

import com.annotation.BindColor;
import com.annotation.BindDimen;
import com.annotation.BindString;
import com.annotation.BindView;
import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * by y.
 * <p>
 * Description:
 */
@AutoService(Processor.class)
public class SimpleProcessor extends AbstractProcessor {
    private Filer filer;
    private HashMap<String, BindClass> map;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        map = new HashMap<>();
        elementUtils = processingEnv.getElementUtils();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            processBindView(roundEnv);
            processBindString(roundEnv);
            processBindColor(roundEnv);
            processBindDimen(roundEnv);
            for (BindClass bindClass : map.values()) {
                bindClass.writeTo().writeTo(filer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void processBindColor(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindColor.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindColor.class).value(),
                    BindEntity.TYPE_COLOR));
        }
    }

    private void processBindDimen(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindDimen.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindDimen.class).value(),
                    BindEntity.TYPE_DIMEN));
        }
    }

    private void processBindString(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindString.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindString.class).value(),
                    BindEntity.TYPE_STRING));
        }
    }

    private void processBindView(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindView.class).value(),
                    BindEntity.TYPE_VIEW));
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }
}