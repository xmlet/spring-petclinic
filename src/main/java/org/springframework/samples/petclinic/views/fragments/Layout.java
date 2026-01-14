package org.springframework.samples.petclinic.views.fragments;

import htmlflow.*;
import org.xmlet.htmlapifaster.*;

import java.lang.Object;
import java.util.function.Consumer;

public class Layout {

	public static HtmlView view(Consumer<Div> content) {
		return HtmlFlow.view(page -> page.html()
			.head()
			.meta()
			.addAttr("http-equiv", "Content-Type")
			.attrContent("text/html; charset=UTF-8")
			.__() // meta
			.meta()
			.attrCharset("utf-8")
			.__() // meta
			.meta()
			.addAttr("http-equiv", "X-UA-Compatible")
			.attrContent("IE=edge")
			.__() // meta
			.meta()
			.attrName("viewport")
			.attrContent("width=device-width, initial-scale=1")
			.__() // meta
			.link()
			.addAttr("rel", "shortcut icon")
			.addAttr("type", "image/x-icon")
			.attrHref("/resources/images/favicon.png")
			.__() // link
			.title()
			.text("PetClinic :: a Spring Framework demonstration")
			.__() // title
			.comment("[if lt IE 9]>\r\n"
					+ "    <script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\"></script>\r\n"
					+ "    <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>\r\n"
					+ "    <![endif]")
			.link()
			.attrRel(EnumRelType.STYLESHEET)
			.attrHref("/resources/css/petclinic.css")
			.__() // link
			.__() // head
			.body()
			.nav()
			.of(Navbar::navbarFragment)
			.__() // nav
			.div()
			.attrClass("container-fluid")
			.div()
			.attrClass("container xd-container")
			.of(div -> content.accept(div))
			.br()
			.__() // br
			.br()
			.__() // br
			.div()
			.attrClass("container")
			.div()
			.attrClass("row")
			.div()
			.attrClass("col-12 text-center")
			.img()
			.attrSrc("/resources/images/spring-pivotal-logo.png")
			.attrAlt("Sponsored by Pivotal")
			.__() // img
			.__() // div
			.__() // div
			.__() // div
			.__() // div
			.__() // div
			.script()
			.attrSrc("/webjars/jquery/2.2.4/jquery.min.js")
			.__() // script
			.script()
			.attrSrc("/webjars/jquery-ui/1.11.4/jquery-ui.min.js")
			.__() // script
			.script()
			.attrSrc("/webjars/bootstrap/3.3.6/js/bootstrap.min.js")
			.__() // script
			.__() // body
			.__() // html
		); // return view
	}

}
