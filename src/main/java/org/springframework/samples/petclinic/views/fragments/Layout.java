package org.springframework.samples.petclinic.views.fragments;

import htmlflow.*;
import org.xmlet.htmlapifaster.*;

import java.lang.Object;
import java.util.function.Consumer;

public class Layout {

    public static HtmlView view(Consumer<Div> content) {
        return HtmlFlow.view(page -> page
            .html()
                .head()
                    .meta().addAttr("http-equiv","Content-Type").attrContent("text/html; charset=UTF-8")
                    .__() //meta
                    .meta().attrCharset("utf-8")
                    .__() //meta
                    .meta().addAttr("http-equiv","X-UA-Compatible").attrContent("IE=edge")
                    .__() //meta
                    .meta().attrName("viewport").attrContent("width=device-width, initial-scale=1")
                    .__() //meta
                    .link().addAttr("rel","shortcut icon").addAttr("type","image/x-icon").attrHref("/resources/images/favicon.png")
                    .__() //link
                    .title()
                        .text("PetClinic :: a Spring Framework demonstration")
                    .__() //title
                    .comment("[if lt IE 9]>\r\n"
+ "    <script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\"></script>\r\n"
+ "    <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>\r\n"
+ "    <![endif]")
                    .link().attrRel(EnumRelType.STYLESHEET).attrHref("/resources/css/petclinic.css")
                    .__() //link
                  .__() //head
                .body()
                    .nav().attrClass("navbar navbar-default").addAttr("role","navigation")
.div().attrClass("container")
                            .div().attrClass("navbar-header")
                                .a().attrClass("navbar-brand").attrHref("/")
                                    .span()
                                    .__() //span
                                .__() //a
                                .button().attrType(EnumTypeButtonType.BUTTON).attrClass("navbar-toggle").addAttr("data-toggle","collapse").addAttr("data-target","#main-navbar")
                                    .span().attrClass("sr-only")
                                            .text("Toggle navigation")
                                    .__() //span
                                    .span().attrClass("icon-bar")
                                    .__() //span
                                    .span().attrClass("icon-bar")
                                    .__() //span
                                    .span().attrClass("icon-bar")
                                    .__() //span
                                .__() //button
                            .__() //div
                            .div().attrClass("navbar-collapse collapse").attrId("main-navbar")
                                .ul().attrClass("nav navbar-nav navbar-right")
                                    .li()
                                        .a().attrHref("")
                                            .span().attrClass("glyphicon  glyphicon-null").addAttr("aria-hidden","true")
                                            .__() //span
                                            .span()
                                            .__() //span
                                        .__() //a
                                    .__() //li
                                    .li().attrClass("active")
                                        .a().attrHref("/").attrTitle("home page")
                                            .span().attrClass("glyphicon  glyphicon-home").addAttr("aria-hidden","true")
                                            .__() //span
                                            .span()
                                                .text("Home")
                                            .__() //span
                                        .__() //a
                                    .__() //li
                                    .li()
                                        .a().attrHref("/owners/find").attrTitle("find owners")
                                            .span().attrClass("glyphicon  glyphicon-search").addAttr("aria-hidden","true")
                                            .__() //span
                                            .span()
                                                .text("Find owners")
                                            .__() //span
                                        .__() //a
                                    .__() //li
                                    .li()
                                        .a().attrHref("/vets.html").attrTitle("veterinarians")
                                            .span().attrClass("glyphicon  glyphicon-th-list").addAttr("aria-hidden","true")
                                            .__() //span
                                            .span()
                                                .text("Veterinarians")
                                            .__() //span
                                        .__() //a
                                    .__() //li
                                    .li()
                                        .a().attrHref("/oups").attrTitle("trigger a RuntimeException to see how it is handled")
                                            .span().attrClass("glyphicon  glyphicon-warning-sign").addAttr("aria-hidden","true")
                                            .__() //span
                                            .span()
                                                .text("Error")
                                            .__() //span
                                        .__() //a
                                    .__() //li
                                .__() //ul
                            .__() //div
                        .__() //div
                    .__() //nav
                    .div().attrClass("container-fluid")
                        .div().attrClass("container xd-container")
                            .of(div -> content.accept(div))
                            .br().__() //br
                            .br().__() //br
                            .div().attrClass("container")
                                .div().attrClass("row")
                                    .div().attrClass("col-12 text-center")
                                        .img().attrSrc("/resources/images/spring-pivotal-logo.png").attrAlt("Sponsored by Pivotal")
                                        .__() //img
                                    .__() //div
                                .__() //div
                            .__() //div
                        .__() //div
                    .__() //div
                    .script().attrSrc("/webjars/jquery/2.2.4/jquery.min.js")
                    .__() //script
                    .script().attrSrc("/webjars/jquery-ui/1.11.4/jquery-ui.min.js")
                    .__() //script
                    .script().attrSrc("/webjars/bootstrap/3.3.6/js/bootstrap.min.js")
                    .__() //script
                .__() //body
            .__() //html
        ); // return view
    }
}
