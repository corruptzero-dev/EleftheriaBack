package ru.corruptzero.eleftheriaback.arch;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    @Test
    public void configurationsShouldBeInConfigurationPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Config$")
                .should().resideInAPackage("..configuration");

        rule.check(importedClasses);
    }

    @Test
    public void controllersShouldBeInControllerPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Controller$")
                .should().resideInAPackage("..controller");

        rule.check(importedClasses);
    }
    @Test
    public void entitiesShouldBeInEntityPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().areAnnotatedWith(Entity.class)
                .should().resideInAPackage("..domain.entity..");

        rule.check(importedClasses);
    }
    @Test
    public void repositoriesShouldBeInRepositoryPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Repository$")
                .should().resideInAPackage("..domain.repository");

        rule.check(importedClasses);
    }

    @Test
    public void repositoriesShouldOnlyBeAccessedByServices() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().resideInAPackage("..repository..")
                .should().onlyBeAccessed().byAnyPackage("..service..", "..unit..");

        rule.check(importedClasses);
    }
    @Test
    public void dtosShouldBeInDtoPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*DTO$")
                .should().resideInAPackage("..dto");

        rule.check(importedClasses);
    }
    @Test
    public void exceptionsShouldBeInExceptionPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Exception$")
                .should().resideInAPackage("..exception");

        rule.check(importedClasses);
    }
    @Test
    public void exceptionHandlersShouldBeInHandlersPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*ExceptionHandler$")
                .should().resideInAPackage("..exception.handler");

        rule.check(importedClasses);
    }

    @Test
    public void mappersShouldBeInMapperPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Mapper$")
                .should().resideInAPackage("..mapper");

        rule.check(importedClasses);
    }

    @Test
    public void serviceInterfacesShouldBeInServicePackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().areInterfaces()
                .and().haveNameMatching(".*Service$")
                .should().resideInAPackage("..service");

        rule.check(importedClasses);
    }
    @Test
    public void serviceInterfacesShouldOnlyBeAccessedByControllersAndImplementations() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().areInterfaces()
                .and().haveNameMatching(".*Service$")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service.impl");

        rule.check(importedClasses);
    }
    @Test
    public void serviceImplementationsShouldBeInImplPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().areAnnotatedWith(Service.class)
                .and().haveNameMatching(".*ServiceImpl$")
                .should().resideInAPackage("..service.impl");

        rule.check(importedClasses);
    }
    @Test
    public void serviceImplementationsShouldBePackagePrivate() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().areAnnotatedWith(Service.class)
                .and().haveNameMatching(".*ServiceImpl$")
                .should().bePackagePrivate();

        rule.check(importedClasses);
    }
}
