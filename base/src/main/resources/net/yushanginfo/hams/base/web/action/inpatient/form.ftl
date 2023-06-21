[#ftl]
[@b.head/]
[@b.toolbar title="修改病人信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(inpatient)]
    <div class="row">
      <div class="col-5">
    [@b.fieldset title="住院信息"  theme="list"]
      [@b.textfield name="inpatient.code" label="住院号" value=inpatient.code! required="true" style="width:100px;" maxlength="20"/]
      [@b.textfield name="inpatient.name" label="姓名" value=inpatient.name! required="true" style="width:100px;" maxlength="20"/]
      [@b.select name="inpatient.gender.id" label="性别" value=inpatient.gender! required="true"  items=genders empty="..."/]
      [@b.select name="inpatient.ward.id" label="病区" value=inpatient.ward! required="true"  items=wards empty="..."/]
      [@b.select name="inpatient.status.id" label="状态" value=inpatient.status! required="true"  items=statuses /]
      [@b.startend label="请假-预计销假" style="width:150px"
            name="inpatient.leaveAt,inpatient.backAt" required="false,false"
            start=inpatient.leaveAt end=inpatient.backAt format="datetime"/]
    [/@]
      </div>
      <div class="col-7">
    [@b.fieldset title="联系人1"  theme="list"]
      [@b.textfield name="relation1.name" label="姓名" value=(relation1.name)! required="true" style="width:100px;" maxlength="20"/]
      [@b.select name="relation1.relationship.id" label="社会关系" value=(relation1.relationship)! required="true"  items=relationships /]
      [@b.textfield name="relation1.phone" label="联系电话" value=(relation1.phone)! required="true" style="width:120px;" maxlength="20"/]
      [@b.textfield name="relation1.subdistrict" label="街道" value=(relation1.subdistrict)! required="true" style="width:300px;" maxlength="50"/]
      [@b.textfield name="relation1.address" label="联系地址" value=(relation1.address)! required="true" style="width:400px;" maxlength="50"/]
    [/@]
      </div>
    </div>

    <div class="row">
      <div class="col-5">
      [@b.fieldset title="联系人2"  theme="list"]
        [@b.textfield name="relation2.name" label="姓名" value=(relation2.name)! style="width:100px;" maxlength="20"/]
        [@b.select name="relation2.relationship.id" label="社会关系" value=(relation2.relationship)!  items=relationships /]
        [@b.textfield name="relation2.phone" label="联系电话" value=(relation2.phone)!   style="width:120px;" maxlength="20"/]
        [@b.textfield name="relation2.subdistrict" label="街道" value=(relation2.subdistrict)!  style="width:300px;" maxlength="50"/]
        [@b.textfield name="relation2.address" label="联系地址" value=(relation2.address)!  style="width:300px;" maxlength="50"/]
      [/@]
      </div>
      <div class="col-7">
      [@b.fieldset title="联系人3"  theme="list"]
        [@b.textfield name="relation3.name" label="姓名" value=(relation3.name)! style="width:100px;" maxlength="20"/]
        [@b.select name="relation3.relationship.id" label="社会关系" value=(relation3.relationship)!  items=relationships /]
        [@b.textfield name="relation3.phone" label="联系电话" value=(relation3.phone)!   style="width:120px;" maxlength="20"/]
        [@b.textfield name="relation3.subdistrict" label="街道" value=(relation3.subdistrict)!  style="width:300px;" maxlength="50"/]
        [@b.textfield name="relation3.address" label="联系地址" value=(relation3.address)!  style="width:400px;" maxlength="30"/]
      [/@]
      </div>
    </div>

    [@b.fieldset  theme="list"]
      [@b.formfoot]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
      [/@]
    [/@]
  [/@]
[@b.foot/]
