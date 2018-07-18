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
class BindViewClass {

    private static final String TARGET = "target";
    private static final String VIEW = "view";
    private Element element;
    private ArrayList<BindViewEntity> entityArrayList;
    private Elements elements;

    BindViewClass() {
        entityArrayList = new ArrayList<>();
    }

    void addField(BindViewEntity field) {
        entityArrayList.add(field);
    }

    void setElement(Element element) {
        this.element = element;
    }

    BindViewClass setElements(Elements elements) {
        this.elements = elements;
        return this;
    }

    JavaFile writeTo() {
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder("bindView")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(element.asType()), TARGET)
                .addParameter(ClassName.bestGuess("android.view.View"), "view");
        for (BindViewEntity entity : entityArrayList) {
            bindViewMethod.addStatement("$N.$N = $N.findViewById($L)", TARGET, entity.name, VIEW, entity.id);
        }
        bindViewMethod.addStatement("this.$N = $N", TARGET, TARGET);
        MethodSpec.Builder unBindBuilder = MethodSpec.methodBuilder("unBind")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class);
        unBindBuilder.addStatement("if (this.$N == null) return", TARGET);
        for (BindViewEntity entity : entityArrayList) {
            unBindBuilder.addStatement("this.$N.$N = null", TARGET, entity.name);
        }
        TypeSpec.Builder builder = TypeSpec.classBuilder(element.getSimpleName() + "_Bind")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("com.api", "ViewBind"), TypeName.get(element.asType())))
                .addField(TypeName.get(element.asType()), TARGET, Modifier.PRIVATE)
                .addMethod(bindViewMethod.build())
                .addMethod(unBindBuilder.build());
        String packageName = elements.getPackageOf(element).getQualifiedName().toString();
        return JavaFile.builder(packageName, builder.build()).build();
    }
}