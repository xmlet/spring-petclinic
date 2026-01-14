package org.springframework.samples.petclinic.views;

import htmlflow.HtmlView;
import org.springframework.samples.petclinic.views.fragments.Layout;
import org.xmlet.htmlapifaster.Div;

public class Error {

	public static HtmlView view = Layout.view(Error::template).threadSafe();

	static void template(Div<?> container) {
		container.img()
			.attrClass("img-responsive")
			.attrSrc("/resources/images/pets.png")
			.__()
			.h2()
			.text("Something happened...")
			.__()
			.p()
			.<Exception>dynamic((p, ex) -> p.text(ex.getMessage()))
			.__();
	}

}
