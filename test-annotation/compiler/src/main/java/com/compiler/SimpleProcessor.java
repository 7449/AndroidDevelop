package com.compiler;

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
    private HashMap<String, BindViewClass> map;
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
            for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
                BindViewClass bindViewClass = map.get(enclosingElement.getQualifiedName().toString());
                if (bindViewClass == null) {
                    bindViewClass = new BindViewClass();
                    bindViewClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                    map.put(enclosingElement.getQualifiedName().toString(), bindViewClass);
                }
                bindViewClass.addField(new BindViewEntity(
                        element.getSimpleName().toString(),
                        element.getAnnotation(BindView.class).value()));
            }
            for (BindViewClass bindViewClass : map.values()) {
                bindViewClass.writeTo().writeTo(filer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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