package org.springframework.samples.petclinic.views.fragments;

import org.xmlet.htmlapifaster.Div;

public class SelectField {

    public static void partialSelectField(Div<?> container, Iterable data, String selected) {
        container.div()
            .attrClass("form-group")
            .label().attrClass("col-sm-2 control-label")
                .text("Type")
            .__() //label
            .div().attrClass("col-sm-10")
                .select().attrId("type").attrName("type")
                    .of(select -> data.forEach(item -> select
                        .option()
                            .attrValue(item.toString())
                            .of(opt -> {
                                if(selected != null && selected.equals(item.toString()))
                                    opt.attrSelected(true);
                            })
                            .text(item)
                        .__() //option
                    ))
                .__() //select
                .span()
                    .attrClass("glyphicon glyphicon-ok form-control-feedback")
                    .addAttr("aria-hidden","true")
                .__() //span
            .__() //div
        .__(); //div
    }
}
