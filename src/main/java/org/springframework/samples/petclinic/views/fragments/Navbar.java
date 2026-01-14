package org.springframework.samples.petclinic.views.fragments;

import org.xmlet.htmlapifaster.EnumTypeButtonType;
import org.xmlet.htmlapifaster.Nav;

public class Navbar {

	public static void navbarFragment(Nav<?> nav) {
		nav.attrClass("navbar navbar-default")
			.addAttr("role", "navigation")
			.div()
			.attrClass("container")
			.div()
			.attrClass("navbar-header")
			.a()
			.attrClass("navbar-brand")
			.attrHref("/")
			.span()
			.__() // span
			.__() // a
			.button()
			.attrType(EnumTypeButtonType.BUTTON)
			.attrClass("navbar-toggle")
			.addAttr("data-toggle", "collapse")
			.addAttr("data-target", "#main-navbar")
			.span()
			.attrClass("sr-only")
			.text("Toggle navigation")
			.__() // span
			.span()
			.attrClass("icon-bar")
			.__() // span
			.span()
			.attrClass("icon-bar")
			.__() // span
			.span()
			.attrClass("icon-bar")
			.__() // span
			.__() // button
			.__() // div
			.div()
			.attrClass("navbar-collapse collapse")
			.attrId("main-navbar")
			.ul()
			.attrClass("nav navbar-nav navbar-right")
			.li()
			.a()
			.attrHref("")
			.span()
			.attrClass("glyphicon  glyphicon-null")
			.addAttr("aria-hidden", "true")
			.__() // span
			.span()
			.__() // span
			.__() // a
			.__() // li
			.li()
			.attrClass("active")
			.a()
			.attrHref("/")
			.attrTitle("home page")
			.span()
			.attrClass("glyphicon  glyphicon-home")
			.addAttr("aria-hidden", "true")
			.__() // span
			.span()
			.text("Home")
			.__() // span
			.__() // a
			.__() // li
			.li()
			.a()
			.attrHref("/owners/find")
			.attrTitle("find owners")
			.span()
			.attrClass("glyphicon  glyphicon-search")
			.addAttr("aria-hidden", "true")
			.__() // span
			.span()
			.text("Find owners")
			.__() // span
			.__() // a
			.__() // li
			.li()
			.a()
			.attrHref("/vets.html")
			.attrTitle("veterinarians")
			.span()
			.attrClass("glyphicon  glyphicon-th-list")
			.addAttr("aria-hidden", "true")
			.__() // span
			.span()
			.text("Veterinarians")
			.__() // span
			.__() // a
			.__() // li
			.li()
			.a()
			.attrHref("/oups")
			.attrTitle("trigger a RuntimeException to see how it is handled")
			.span()
			.attrClass("glyphicon  glyphicon-warning-sign")
			.addAttr("aria-hidden", "true")
			.__() // span
			.span()
			.text("Error")
			.__() // span
			.__() // a
			.__() // li
			.__() // ul
			.__() // div
			.__(); // div
	}

}
