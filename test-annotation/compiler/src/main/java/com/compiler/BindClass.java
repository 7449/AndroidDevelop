package com.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * by y.
 * <p>
 * Description:
 */
class BindClass {

    private static final String TARGET = "target";
    private static final String VIEW = "view";
    private static final String RES = "res";

    private Element element;
    private ArrayList<BindEntity> entityArrayList;
    private Elements elements;

    BindClass() {
        entityArrayList = new ArrayList<>();
    }

    void addField(BindEntity field) {
        entityArrayList.add(field);
    }

    void setElement(Element element) {
        this.element = element;
    }

    BindClass setElements(Elements elements) {
        this.elements = elements;
        return this;
    }

    JavaFile writeTo() {
        return JavaFile.builder(elements.getPackageOf(element).getQualifiedName().toString(), initTypeSpec()).build();
    }


    private TypeSpec initTypeSpec() {
        return TypeSpec.classBuilder(element.getSimpleName() + "_Bind")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("com.api", "ViewBind"), TypeName.get(element.asType())))
                .addField(TypeName.get(element.asType()), TARGET, Modifier.PRIVATE)
                .addMethod(initBindMethod())
                .addMethod(initUnBindMethod())
                .build();
    }

    private MethodSpec initBindMethod() {
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder("bindView")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(element.asType()), TARGET)
                .addStatement("this.$N = $N", TARGET, TARGET)
                .addStatement("$T context = $N.getContext()", ClassName.get("android.content", "Context"), VIEW)
                .addStatement("$T res = context.getResources()", ClassName.get("android.content.res", "Resources"))
                .addParameter(ClassName.bestGuess("android.view.View"), "view");
        for (BindEntity entity : entityArrayList) {
            switch (entity.type) {
                case BindEntity.TYPE_STRING:
                    bindViewMethod.addStatement("$N.$N = $N.getString($L)", TARGET, entity.name, RES, entity.id);
                    break;
                case BindEntity.TYPE_VIEW:
                    bindViewMethod.addStatement("$N.$N = $N.findViewById($L)", TARGET, entity.name, VIEW, entity.id);
                    break;
                case BindEntity.TYPE_COLOR:
                    bindViewMethod.addStatement("$N.$N = $N.getColor($L)", TARGET, entity.name, RES, entity.id);
                    break;
                case BindEntity.TYPE_DIMEN:
                    bindViewMethod.addStatement("$N.$N = $N.getDimension($L)", TARGET, entity.name, RES, entity.id);
                    break;
            }
        }
        return bindViewMethod.build();
    }

    private MethodSpec initUnBindMethod() {
        MethodSpec.Builder unBindBuilder = MethodSpec.methodBuilder("unBind")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("if (this.$N == null) return", TARGET)
                .addAnnotation(Override.class);
        for (BindEntity entity : entityArrayList) {
            if (entity.type == BindEntity.TYPE_VIEW) {
                unBindBuilder.addStatement("this.$N.$N = null", TARGET, entity.name);
            }
        }
        unBindBuilder.addStatement("this.$N = null", TARGET);
        return unBindBuilder.build();
    }
}