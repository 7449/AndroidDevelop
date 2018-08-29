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
        return TypeSpec.classBuilder(element.getSimpleName() + BindConst.CLASS_SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("com.api", "ViewBind"), TypeName.get(element.asType())))
                .addField(TypeName.get(element.asType()), BindConst.M_TARGET, Modifier.PRIVATE)
                .addMethod(initBindMethod())
                .addMethod(initUnBindMethod())
                .build();
    }

    private MethodSpec initBindMethod() {
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder(BindConst.METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(element.asType()), BindConst.TARGET)
                .addStatement(BindConst.STATEMENT_TARGET)
                .addStatement(BindConst.STATEMENT_CONTEXT, ClassName.get(BindConst.PACKAGE_CONTENT, "Context"), BindConst.VIEW)
                .addStatement(BindConst.STATEMENT_RESOURCES, ClassName.get(BindConst.PACKAGE_RES, "Resources"))
                .addParameter(ClassName.bestGuess(BindConst.PACKAGE_VIEW), "view");
        for (BindEntity entity : entityArrayList) {
            switch (entity.type) {
                case BindConst.TYPE_STRING:
                    bindViewMethod.addStatement(BindConst.STATEMENT_STRING, entity.name, BindConst.RES, entity.id);
                    break;
                case BindConst.TYPE_VIEW:
                    bindViewMethod.addStatement(BindConst.STATEMENT_VIEW, entity.name, BindConst.VIEW, entity.id);
                    break;
                case BindConst.TYPE_COLOR:
                    bindViewMethod.addStatement(BindConst.STATEMENT_COLOR, entity.name, BindConst.RES, entity.id);
                    break;
                case BindConst.TYPE_DIMEN:
                    bindViewMethod.addStatement(BindConst.STATEMENT_DIMENSION, entity.name, BindConst.RES, entity.id);
                    break;
                case BindConst.TYPE_DRAWABLE:
                    bindViewMethod.addStatement(BindConst.STATEMENT_DRAWABLE, entity.name, BindConst.RES, entity.id);
                    break;
                case BindConst.TYPE_INT_ARRAY:
                    bindViewMethod.addStatement(BindConst.STATEMENT_INT_ARRAY, entity.name, BindConst.RES, entity.id);
                    break;
                case BindConst.TYPE_STRING_ARRAY:
                    bindViewMethod.addStatement(BindConst.STATEMENT_STRING_ARRAY, entity.name, BindConst.RES, entity.id);
                    break;
            }
        }
        return bindViewMethod.build();
    }

    private MethodSpec initUnBindMethod() {
        MethodSpec.Builder unBindBuilder = MethodSpec.methodBuilder(BindConst.UnBindConst.METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addStatement(BindConst.UnBindConst.STATEMENT_RETURN)
                .addAnnotation(Override.class);
        for (BindEntity entity : entityArrayList) {
            if (entity.type != BindConst.TYPE_COLOR && entity.type != BindConst.TYPE_DIMEN) {
                unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_NULL, entity.name);
            }
        }
        unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_TARGET_NULL);
        return unBindBuilder.build();
    }
}