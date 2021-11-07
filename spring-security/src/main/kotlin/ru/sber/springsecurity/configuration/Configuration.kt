package ru.sber.springsecurity.configuration

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber.springsecurity.service")
@ServletComponentScan("ru.sber.springsecurity.servlets", "ru.sber.springsecurity.filter")
open class Configuration
