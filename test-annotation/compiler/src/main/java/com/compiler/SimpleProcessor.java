package com.compiler;

import com.annotation.BindClick;
import com.annotation.BindColor;
import com.annotation.BindDimen;
import com.annotation.BindDrawable;
import com.annotation.BindIntArray;
import com.annotation.BindLongClick;
import com.annotation.BindString;
import com.annotation.BindStringArray;
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
            processBindDrawable(roundEnv);
            processBindStringArray(roundEnv);
            processBindIntArray(roundEnv);
            processBindClick(roundEnv);
            processBindLongClick(roundEnv);
            for (BindClass bindClass : map.values()) {
                bindClass.writeTo().writeTo(filer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void processBindLongClick(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindLongClick.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindLongClick.class).value(),
                    BindConst.TYPE_LONG_CLICK));
        }
    }

    private void processBindClick(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindClick.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindClick.class).value(),
                    BindConst.TYPE_CLICK));
        }
    }

    private void processBindIntArray(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindIntArray.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindIntArray.class).value(),
                    BindConst.TYPE_INT_ARRAY));
        }
    }

    private void processBindStringArray(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindStringArray.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindStringArray.class).value(),
                    BindConst.TYPE_STRING_ARRAY));
        }
    }

    private void processBindDrawable(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindDrawable.class)) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            BindClass bindClass = map.get(enclosingElement.getQualifiedName().toString());
            if (bindClass == null) {
                bindClass = new BindClass();
                bindClass.setElements(elementUtils).setElement(element.getEnclosingElement());
                map.put(enclosingElement.getQualifiedName().toString(), bindClass);
            }
            bindClass.addField(new BindEntity(
                    element.getSimpleName().toString(),
                    element.getAnnotation(BindDrawable.class).value(),
                    BindConst.TYPE_DRAWABLE));
        }
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
                    BindConst.TYPE_COLOR));
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
                    BindConst.TYPE_DIMEN));
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
                    BindConst.TYPE_STRING));
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
                    BindConst.TYPE_VIEW));
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