package com.mts.nrtrde.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SliderField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;


public class FormsExample extends LayoutContainer {

	  private HorizontalPanel vp;

	  private FormData formData;

	@Override
	protected void onRender(com.google.gwt.user.client.Element parent, int index) {
		// TODO Auto-generated method stub
		super.onRender(parent, index);
		
		 super.onRender(parent, index);
		    formData = new FormData("-20");
		    vp = new HorizontalPanel();
	//	    vp.setSpacing(10);
		    createForm1();
		    add(vp);
		  }

		  private void createForm1() {
		    FormPanel simple = new FormPanel();
		    simple.setLayout(new RowLayout(Orientation.HORIZONTAL));
		    simple.setHeading("Simple Form");
		    simple.setFrame(true);
		    simple.setWidth(350);

		    TextField<String> firstName = new TextField<String>();
		    firstName.setFieldLabel("Name");
		    firstName.setAllowBlank(false);
		    simple.add(firstName, formData);

		    TextField<String> email = new TextField<String>();
		    email.setFieldLabel("Email");
		    email.setAllowBlank(false);
		    simple.add(email, formData);

		    DateField date = new DateField();
		    date.setFieldLabel("Birthday");
		    simple.add(date, formData);

		    TimeField time = new TimeField();
		    time.setFieldLabel("Time");
		    simple.add(time, formData);

		    Slider slider = new Slider();
		    slider.setMinValue(40);
		    slider.setMaxValue(90);
		    slider.setValue(60);
		    slider.setIncrement(1);
		    slider.setMessage("{0} inches tall");

		    final SliderField sf = new SliderField(slider);
		    sf.setFieldLabel("Size");
		    simple.add(sf, formData);
		    CheckBox check1 = new CheckBox();
		    check1.setBoxLabel("Classical");

		    CheckBox check2 = new CheckBox();
		    check2.setBoxLabel("Rock");
		    check2.setValue(true);

		    CheckBox check3 = new CheckBox();
		    check3.setBoxLabel("Blue");

		    CheckBoxGroup checkGroup = new CheckBoxGroup();
		    checkGroup.setFieldLabel("Music");
		    checkGroup.add(check1);
		    checkGroup.add(check2);
		    checkGroup.add(check3);
		    simple.add(checkGroup, formData);

		    Radio radio = new Radio();
		    radio.setBoxLabel("Red");
		    radio.setValue(true);

		    Radio radio2 = new Radio();
		    radio2.setBoxLabel("Blue");

		    RadioGroup radioGroup = new RadioGroup();
		    radioGroup.setFieldLabel("Favorite Color");
		    radioGroup.add(radio);
		    radioGroup.add(radio2);
		    simple.add(radioGroup, formData);

		    Radio radio3 = new Radio();
		    radio3.setBoxLabel("Apple");
		    radio3.setValue(true);

		    Radio radio4 = new Radio();
		    radio4.setBoxLabel("Banana");

		    RadioGroup radioGroup2 = new RadioGroup();
		    radioGroup2.setFieldLabel("Favorite Fruit");
		    radioGroup2.add(radio3);
		    radioGroup2.add(radio4);
		    simple.add(radioGroup2, formData);

		    TextArea description = new TextArea();
		    description.setPreventScrollbars(true);
		    description.setFieldLabel("Description");
		    simple.add(description, formData);

		    Button b = new Button("Submit");
		    simple.addButton(b);
		    simple.addButton(new Button("Cancel"));

		    simple.setButtonAlign(HorizontalAlignment.CENTER);

		    FormButtonBinding binding = new FormButtonBinding(simple);
		    binding.addButton(b);

		    vp.add(simple);

	}
}