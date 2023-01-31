package org.springframework.samples.petclinic.views;

import htmlflow.HtmlView;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.views.fragments.Layout;
import org.springframework.stereotype.Component;
import org.xmlet.htmlapifaster.Div;
import org.xmlet.htmlapifaster.EnumMethodType;
import org.xmlet.htmlapifaster.EnumTypeButtonType;
import org.xmlet.htmlapifaster.EnumTypeInputType;

import java.util.List;
import java.util.function.Function;

import static org.springframework.samples.petclinic.views.fragments.InputField.partialInputField;
import static org.springframework.samples.petclinic.views.fragments.SelectField.partialSelectField;

@Component
public class CreateOrUpdatePetForm {

    private final PetRepository pets;
    private final List<PetType> petTypes;
    public final HtmlView view;

    public CreateOrUpdatePetForm(PetRepository pets) {
        this.pets = pets;
        this.petTypes = pets.findPetTypes();
        this.view = Layout.view(this::template);
    }

    public void template(Div<?> container) {
            container
                .h2()
                    .<Pet>dynamic((h2, pet) -> {if(pet == null) h2.text("New "); })
                    .text("Pet")
                .__() //h2
                .form().attrClass("form-horizontal").attrMethod(EnumMethodType.POST)
                    .input().attrType(EnumTypeInputType.HIDDEN).attrName("id").attrValue("")
                    .__() //input
                    .div().attrClass("form-group has-feedback")
                        .div().attrClass("form-group")
                            .label().attrClass("col-sm-2 control-label")
                                .text("Owner")
                            .__() //label
                            .div().attrClass("col-sm-10")
                                .span()
                                    .<Pet>dynamic((span, pet) ->
                                        span.text(from(pet, p -> p.getOwner() != null ? p.getOwner().getFirstName() + " " + p.getOwner().getLastName() : ""))
                                    )
                                .__() //span
                            .__() //div
                        .__() //div
                        .<Pet>dynamic((div, pet) -> partialInputField(div, "Name", "name", from(pet, o -> o.getName())))
                        .<Pet>dynamic((div, pet) -> partialInputField(div, "Birth Date", "birthDate", from(pet, p -> p.getBirthDate())))
                        .<Pet>dynamic((div, pet) -> partialSelectField(div, petTypes, from(pet, p -> p.getType())))
                    .__() //div
                    .div().attrClass("form-group")
                        .div().attrClass("col-sm-offset-2 col-sm-10")
                            .button().attrClass("btn btn-default").attrType(EnumTypeButtonType.SUBMIT)
                                .<Pet>dynamic((bt, pet) -> {
                                    if(pet == null || pet.getName() == null) bt.text("Add Pet");
                                    else bt.text("Update Pet");
                                })
                            .__() //button
                        .__() //div
                    .__() //div
                .__(); //form
    }

    static <T, R> String from(T target, Function<T, R> getter) {
        Object val = target == null ? "" : getter.apply(target);
        return val != null ? val.toString() : "";
    }
}
