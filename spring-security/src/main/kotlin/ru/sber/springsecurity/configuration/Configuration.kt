package ru.sber.springsecurity.configuration

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan("ru.sber.springsecurity.service")
@ServletComponentScan("ru.sber.springsecurity.filter")
class Configuration : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/login").setViewName("login")
    }
}
