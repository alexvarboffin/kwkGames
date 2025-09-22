//package com.walhalla.annotations
//
//import com.google.auto.service.AutoService
//import com.squareup.kotlinpoet.*
//import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
//
//import java.util.*
//import javax.annotation.processing.*
//import javax.lang.model.SourceVersion
//import javax.lang.model.element.TypeElement
//import javax.lang.model.element.Element
//import javax.lang.model.element.VariableElement
//import javax.tools.Diagnostic
//
//@AutoService(Processor::class)
//@SupportedSourceVersion(SourceVersion.RELEASE_17)
//@SupportedAnnotationTypes("com.yourcompany.annotations.Encrypt")
//class StringProcessor : AbstractProcessor() {
//
//    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
//        val elements = roundEnv.getElementsAnnotatedWith(Encrypt::class.java)
//
//        if (elements.isEmpty()) return false
//
//        elements.forEach { element ->
//            if (element !is VariableElement) {
//                processingEnv.messager.printMessage(
//                    Diagnostic.Kind.ERROR,
//                    "@Encrypt can only be applied to fields",
//                    element
//                )
//                return@forEach
//            }
//
//            val enclosingClass = element.enclosingElement as TypeElement
//            val packageName = processingEnv.elementUtils.getPackageOf(enclosingClass).toString()
//            val className = enclosingClass.simpleString()
//
//            generateDecryptionClass(packageName, className, element)
//        }
//
//        return true
//    }
//
//    private fun generateDecryptionClass(packageName: String, className: String, element: VariableElement) {
//        val fieldName = element.simpleString()
//        val initializer = element.constantValue?.toString() ?: return
//
//        // Шифруем строку
//        val encrypted = Base64.getEncoder().encodeToString(initializer.toByteArray())
//
//        // Генерируем класс-дешифратор
//        val decryptorClassName = "${className}Decryptor"
//
//        val file = FileSpec.builder(packageName, decryptorClassName)
//            .addType(
//                TypeSpec.objectBuilder(decryptorClassName)
//                    .addFunction(
//                        FunSpec.builder("decrypt$fieldName")
//                            .returns(String::class)
//                            .addStatement(
//                                "return String(android.util.Base64.decode(%S, android.util.Base64.DEFAULT))",
//                                encrypted
//                            )
//                            .build()
//                    )
//                    .build()
//            )
//            .build()
//
//        // Записываем сгенерированный код
//        try {
//            file.writeTo(processingEnv.filer)
//        } catch (e: Exception) {
//            processingEnv.messager.printMessage(
//                Diagnostic.Kind.ERROR,
//                "Failed to generate decryption class: ${e.message}"
//            )
//        }
//    }
//}