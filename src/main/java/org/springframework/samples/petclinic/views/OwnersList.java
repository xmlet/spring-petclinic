package org.springframework.samples.petclinic.views;

import htmlflow.HtmlView;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.views.fragments.Layout;
import org.xmlet.htmlapifaster.Div;

import java.util.Collection;

public class OwnersList {

    public static final HtmlView view = Layout.view(OwnersList::template).threadSafe();

    static void template(Div<?> container) {
        container
                .h2()
                    .text("Owners")
                .__() //h2
                .table().attrId("owners").attrClass("table table-striped")
                    .thead()
                        .tr()
                            .th().attrStyle("width: 150px;")
                                .text("Name")
                            .__() //th
                            .th().attrStyle("width: 200px;")
                                .text("Address")
                            .__() //th
                            .th()
                                .text("City")
                            .__() //th
                            .th().attrStyle("width: 120px")
                                .text("Telephone")
                            .__() //th
                            .th()
                                .text("Pets")
                            .__() //th
                        .__() //tr
                    .__() //thead
                    .tbody()
                        .<Collection<Owner>>dynamic((tbody, owners) -> owners.forEach(ow -> tbody
                            .tr()
                                .td()
                                    .a()
                                        .attrHref("/owners/" + ow.getId())
                                        .text(ow.getFirstName() + " " + ow.getLastName())
                                    .__()
                                .__()
                                .td().text(ow.getAddress()).__()
                                .td().text(ow.getCity()).__()
                                .td().text(ow.getTelephone()).__()
                                .td().span().of(span -> ow.getPets().forEach(pet ->
                                    span.text(pet.getName() + " ").__()
                                )).__()
                            .__()
                        ))
                    .__() //tbody
                .__(); //table
    }
}

