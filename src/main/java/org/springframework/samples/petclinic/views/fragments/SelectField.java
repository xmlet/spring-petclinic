package org.springframework.samples.petclinic.views.fragments;

import org.xmlet.htmlapifaster.Div;

public class SelectField {

    public static class DS {
        final Iterable data;
        final String selected;

        public DS(Iterable data, String selected) {
            this.data = data;
            this.selected = selected;
        }
        public static DS of(Iterable data, String selected) {
            return new DS(data, selected);
        }
    }

    public static void partialSelectField(Div<?> container, DS src) {
        container.div()
            .attrClass("form-group")
            .label().attrClass("col-sm-2 control-label")
                .text("Type")
            .__() //label
            .div().attrClass("col-sm-10")
                .select().attrId("type").attrName("type")
                    .of(select -> src.data.forEach(item -> select
                        .option()
                            .attrValue(item.toString())
                            .of(opt -> {
                                if(src.selected != null && src.selected.equals(item.toString()))
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
