package com.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Collections;

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
    private ArrayList<ClickHelper> tempClickHelper;
    private Elements elements;

    BindClass() {
        entityArrayList = new ArrayList<>();
        tempClickHelper = new ArrayList<>();
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


        TypeSpec.Builder viewBind = TypeSpec.classBuilder(element.getSimpleName() + BindConst.CLASS_SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("com.api", "ViewBind"), TypeName.get(element.asType())))
                .addField(TypeName.get(element.asType()), BindConst.M_TARGET, Modifier.PRIVATE);
        for (BindEntity bindEntity : entityArrayList) {
            if ((bindEntity.type == BindConst.TYPE_CLICK || bindEntity.type == BindConst.TYPE_LONG_CLICK) && hasField(bindEntity)) {
                ClickHelper clickHelper = hasTempClickHelper(bindEntity.id);
                if (clickHelper == null) {
                    clickHelper = new ClickHelper();
                    clickHelper.id = bindEntity.id;
                    initClickHelper(bindEntity, clickHelper);
                    viewBind.addField(ClassName.bestGuess(BindConst.PACKAGE_VIEW), BindConst.VIEW + bindEntity.id, Modifier.PRIVATE);
                    tempClickHelper.add(clickHelper);
                } else {
                    initClickHelper(bindEntity, clickHelper);
                }
            }
        }
        return viewBind
                .addMethod(initBindMethod())
                .addMethod(initUnBindMethod())
                .build();
    }

    private void initClickHelper(BindEntity bindEntity, ClickHelper clickHelper) {
        if (bindEntity.type == BindConst.TYPE_CLICK) {
            clickHelper.hasClick = true;
            clickHelper.clickMethodName = bindEntity.name;
        }
        if (bindEntity.type == BindConst.TYPE_LONG_CLICK) {
            clickHelper.hasLongClick = true;
            clickHelper.longClickMethodName = bindEntity.name;
        }
    }

    private MethodSpec initBindMethod() {
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder(BindConst.METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(element.asType()), BindConst.TARGET)
                .addStatement(BindConst.STATEMENT_TARGET)
                .addStatement(BindConst.STATEMENT_CONTEXT, ClassName.get(BindConst.PACKAGE_CONTENT, "Context"), BindConst.VIEW)
                .addStatement(BindConst.STATEMENT_RESOURCES, ClassName.get(BindConst.PACKAGE_RES, "Resources"))
                .addParameter(ClassName.bestGuess(BindConst.PACKAGE_VIEW), BindConst.VIEW);
        for (BindEntity entity : entityArrayList) {
            switch (entity.type) {
                case BindConst.TYPE_STRING:
                    bindViewMethod.addStatement(BindConst.STATEMENT_STRING, entity.name, BindConst.RES, entity.id);
                    break;
                case BindConst.TYPE_VIEW:
                    bindViewMethod.addStatement(BindConst.STATEMENT_TARGET_VIEW, entity.name, BindConst.VIEW, entity.id);
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
                case BindConst.TYPE_BITMAP:
                    ClassName bitmapFactory = ClassName.get(BindConst.PACKAGE_GRAPHICS, "BitmapFactory");
                    bindViewMethod.addStatement(BindConst.STATEMENT_BITMAP, entity.name, bitmapFactory, BindConst.RES, entity.id);
                    break;
                case BindConst.TYPE_CLICK:
                    String click = hasView(entity);
                    if (click != null) {
                        bindViewMethod.addStatement(BindConst.STATEMENT_VIEW_CLICK, click, entity.name);
                    }
                    break;
                case BindConst.TYPE_LONG_CLICK:
                    String longClick = hasView(entity);
                    if (longClick != null) {
                        bindViewMethod.addStatement(BindConst.STATEMENT_VIEW_LONG_CLICK, longClick, entity.name);
                    }
                    break;
            }
        }
        for (ClickHelper clickHelper : tempClickHelper) {
            bindViewMethod.addStatement(BindConst.STATEMENT_VIEW, BindConst.VIEW + clickHelper.id, clickHelper.id);
            if (clickHelper.hasClick) {
                bindViewMethod.addStatement(BindConst.STATEMENT_CLICK, BindConst.VIEW + clickHelper.id, clickHelper.clickMethodName);
            }
            if (clickHelper.hasLongClick) {
                bindViewMethod.addStatement(BindConst.STATEMENT_LONG_CLICK, BindConst.VIEW + clickHelper.id, clickHelper.longClickMethodName);
            }
        }
        return bindViewMethod.build();
    }

    private MethodSpec initUnBindMethod() {
        MethodSpec.Builder unBindBuilder = MethodSpec.methodBuilder(BindConst.UnBindConst.METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addStatement(BindConst.UnBindConst.STATEMENT_RETURN)
                .addAnnotation(Override.class);
        Collections.reverse(entityArrayList);
        for (BindEntity entity : entityArrayList) {
            switch (entity.type) {
                case BindConst.TYPE_VIEW:
                case BindConst.TYPE_STRING:
                case BindConst.TYPE_DRAWABLE:
                case BindConst.TYPE_STRING_ARRAY:
                case BindConst.TYPE_INT_ARRAY:
                case BindConst.TYPE_BITMAP:
                    unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_VIEW_NULL, entity.name);
                    break;
                case BindConst.TYPE_CLICK:
                    String click = hasView(entity);
                    if (click != null) {
                        unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_VIEW_CLICK_NULL, click);
                    }
                    break;
                case BindConst.TYPE_LONG_CLICK:
                    String longClick = hasView(entity);
                    if (longClick != null) {
                        unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_VIEW_LONG_CLICK_NULL, longClick);
                    }
                    break;
            }
        }
        for (ClickHelper clickHelper : tempClickHelper) {
            if (clickHelper.hasClick) {
                unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_CLICK_NULL, BindConst.VIEW + clickHelper.id);
            }
            if (clickHelper.hasLongClick) {
                unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_LONG_CLICK_NULL, BindConst.VIEW + clickHelper.id);
            }
            unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_NULL, BindConst.VIEW + clickHelper.id);
        }
        return unBindBuilder.addStatement(BindConst.UnBindConst.STATEMENT_TARGET_NULL).build();
    }

    private ClickHelper hasTempClickHelper(int id) {
        for (ClickHelper clickHelper : tempClickHelper) {
            if (clickHelper.id == id) {
                return clickHelper;
            }
        }
        return null;
    }

    private boolean hasField(BindEntity entity) {
        for (BindEntity bindEntity : entityArrayList) {
            if (bindEntity.type == BindConst.TYPE_VIEW && bindEntity.id == entity.id) {
                return false;
            }
        }
        return true;
    }

    private String hasView(BindEntity entity) {
        for (BindEntity bindEntity : entityArrayList) {
            if (bindEntity.type == BindConst.TYPE_VIEW && bindEntity.id == entity.id) {
                return bindEntity.name;
            }
        }
        return null;
    }
}