package org.springframework.samples.petclinic.views;

import htmlflow.HtmlView;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.views.fragments.Layout;
import org.xmlet.htmlapifaster.Div;
import org.xmlet.htmlapifaster.EnumMethodType;
import org.xmlet.htmlapifaster.EnumTypeButtonType;

import java.util.function.Function;

import static org.springframework.samples.petclinic.views.fragments.InputField.partialInputField;

public class CreateOrUpdateOwnerForm {

	public static final HtmlView view = Layout.view(CreateOrUpdateOwnerForm::template).threadSafe();

	static void template(Div<?> container) {
		container.h2()
			.text("Owner")
			.__() // h2
			.form()
			.attrClass("form-horizontal")
			.attrId("add-owner-form")
			.attrMethod(EnumMethodType.POST)
			.div()
			.attrClass("form-group has-feedback")
			.<Owner>dynamic((div, owner) -> partialInputField(div, "First Name", "firstName",
					from(owner, o -> o.getFirstName())))
			.<Owner>dynamic(
					(div, owner) -> partialInputField(div, "Last Name", "lastName", from(owner, o -> o.getLastName())))
			.<Owner>dynamic(
					(div, owner) -> partialInputField(div, "Address", "address", from(owner, o -> o.getAddress())))
			.<Owner>dynamic((div, owner) -> partialInputField(div, "City", "city", from(owner, o -> o.getCity())))
			.<Owner>dynamic((div, owner) -> partialInputField(div, "Telephone", "telephone",
					from(owner, o -> o.getTelephone())))
			.__() // div
			.div()
			.attrClass("form-group")
			.div()
			.attrClass("col-sm-offset-2 col-sm-10")
			.button()
			.attrClass("btn btn-default")
			.attrType(EnumTypeButtonType.SUBMIT)
			.<Owner>dynamic((bt, owner) -> {
				if (owner == null)
					bt.text("Add Owner");
				else
					bt.text("Update Owner");
			})
			.__() // button
			.__() // div
			.__() // div
			.__(); // form
	}

	static <T, R> String from(T target, Function<T, R> getter) {
		return target == null ? "" : getter.apply(target).toString();
	}

}
