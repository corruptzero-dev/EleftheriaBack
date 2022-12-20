package ru.corruptzero.eleftheriaback.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {
    @Test
    public void servicesShouldBeInServicePackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().areAnnotatedWith(Service.class)
                .or().haveNameMatching(".*ServiceImpl$")
                .should().resideInAPackage("..service..");

        rule.check(importedClasses);
    }
    @Test
    public void servicesShouldOnlyBeAccessedByControllers() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");
        
        rule.check(importedClasses);

    }
    @Test
    public void repositoriesShouldBeInRepositoryPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Repository$")
                .should().resideInAPackage("..repository");

        rule.check(importedClasses);
    }
    @Test
    public void configurationsShouldBeInConfigurationPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ru.corruptzero.eleftheriaback");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Repository$")
                .should().resideInAPackage("..repository");

        rule.check(importedClasses);
    }

}
