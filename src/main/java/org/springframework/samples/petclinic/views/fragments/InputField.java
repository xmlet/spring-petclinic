package org.springframework.samples.petclinic.views.fragments;

import org.xmlet.htmlapifaster.Div;
import org.xmlet.htmlapifaster.EnumTypeInputType;

public class InputField {

    public static class LV {
        final String label;
        final String id;
        final Object value;

        public LV(String label, String id, Object value) {
            this.label = label;
            this.id = id;
            this.value = value;
        }

        public static LV of(String label, String id,  Object value) {
            return new LV(label, id, value);
        }
    }

    public static void partialInputField(Div<?> container, LV model) {
        container
            .div().attrClass("form-group")
                .label().attrClass("col-sm-2 control-label")
                    .of(label -> label.text(model.label))
                .__() //label
                .div().attrClass("col-sm-10")
                    .div()
                        .input()
                            .attrClass("form-control")
                            .attrType(EnumTypeInputType.TEXT)
                            .attrId(model.id)
                            .attrName(model.id)
                            .attrValue(model.value.toString())
                            .of(input -> { if(model.label.toLowerCase().contains("date")) input
                                .attrPlaceholder("YYYY-MM-DD")
                                .attrTitle("Enter a date in this format: YYYY-MM-DD")
                                .attrPattern("(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))");
                            })
                        .__() // input
                    .__() // div
                    .span().attrClass("glyphicon glyphicon-ok form-control-feedback").addAttr("aria-hidden","true").__()
                .__() // div
            .__(); // div
    }
}
