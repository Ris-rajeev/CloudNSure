package com.realnet.DataTypesWireframe.Services;
import com.realnet.DataTypesWireframe.Repository.DataTypesWireframe_Repository;
import com.realnet.DataTypesWireframe.Entity.DataTypesWireframe_t;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DataTypesWireframe_Service {
	@Autowired
	private DataTypesWireframe_Repository Repository;
	public DataTypesWireframe_t Savedata(DataTypesWireframe_t data) {
		return Repository.save(data);
	}
	public List<DataTypesWireframe_t> getdetails() {
		return (List<DataTypesWireframe_t>) Repository.findAll();
	}
	public DataTypesWireframe_t getdetailsbyId(Long id) {
		return Repository.findById(id).get();
	}
	public void delete_by_id(Long id) {
		Repository.deleteById(id);
	}
	public DataTypesWireframe_t update(DataTypesWireframe_t data, Long id) {
		DataTypesWireframe_t old = Repository.findById(id).get();
		old.setText(data.getText());
		old.setPassword(data.getPassword());
		old.setNumber(data.getNumber());
		old.setDate(data.getDate());
		old.setDateTime(data.getDateTime());
		old.setParagraph(data.getParagraph());
		old.setTextArea(data.getTextArea());
		old.setDropDown(data.getDropDown());
		old.setAutoComplete(data.getAutoComplete());
		old.setRadioField(data.isRadioField());
		old.setCheckbox(data.isCheckbox());
		old.setEmail(data.getEmail());
		old.setPhoneNumber(data.getPhoneNumber());
		old.setCalculatedField(data.getCalculatedField());
		old.setSection(data.getSection());
		old.setDivision(data.getDivision());
		old.setFileUpload(data.getFileUpload());
		old.setSelectField(data.getSelectField());
		old.setButton(data.getButton());
		old.setMultiselect(data.getMultiselect());
		old.setTagsField(data.getTagsField());
		old.setImage(data.getImage());
		old.setAudioField(data.getAudioField());
		old.setUrl(data.getUrl());
		old.setDecima(data.getDecima());
		old.setRecaptchaField(data.getRecaptchaField());
		old.setHtmlElement(data.getHtmlElement());
		old.setPannel(data.getPannel());
		old.setTabField(data.getTabField());
		old.setAddressGroup(data.getAddressGroup());
		old.setSignatureField(data.getSignatureField());
		old.setPercent(data.getPercent());
		old.setCurrency(data.getCurrency());
		old.setFieldSet(data.getFieldSet());
		old.setTableField(data.getTableField());
		old.setHidden(data.getHidden());
		old.setEditGrid(data.getEditGrid());
		old.setTree(data.getTree());
		old.setContent(data.getContent());
		old.setElement(data.getElement());
		old.setColomn(data.getColomn());
		old.setDataMap(data.getDataMap());
		old.setDataGrid(data.getDataGrid());
		final DataTypesWireframe_t test = Repository.save(old);
		return test;
	}
}














