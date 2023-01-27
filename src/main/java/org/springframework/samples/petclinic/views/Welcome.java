package org.springframework.samples.petclinic.views;

import org.xmlet.htmlapifaster.Div;

public class Welcome {
    public static void template (Div<?> container) {
        container
            .div()
                .h2()
                    .text("Welcome")
                .__() //h2
                .div().attrClass("row")
                    .div().attrClass("col-md-12")
                        .img().attrClass("img-responsive").attrSrc("/resources/images/pets.png")
                        .__() //img
                    .__() //div
                .__() //div
            .__();
    }
}
