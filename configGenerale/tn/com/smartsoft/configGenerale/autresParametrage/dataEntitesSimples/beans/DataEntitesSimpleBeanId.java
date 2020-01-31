package tn.com.smartsoft.configGenerale.autresParametrage.dataEntitesSimples.beans;
import java.io.Serializable;
import tn.com.smartsoft.commons.utils.ValueUtils;
public class DataEntitesSimpleBeanId implements Serializable{
private static final long serialVersionUID = 1L;
 private String dataEntitesSimplesId;
 private String entitesSimplesId;
 public DataEntitesSimpleBeanId(){
  super();
 }
 public DataEntitesSimpleBeanId(DataEntitesSimpleBean dataEntitesSimpleBean){
  super();
   this.setDataEntitesSimplesId(dataEntitesSimpleBean.getDataEntitesSimplesId());
   this.setEntitesSimplesId(dataEntitesSimpleBean.getEntitesSimplesId());
 }
 public void copyValue(DataEntitesSimpleBean objectValue){
    objectValue.setDataEntitesSimplesId(this.getDataEntitesSimplesId());
   objectValue.setEntitesSimplesId(this.getEntitesSimplesId());

 }
 public String getDataEntitesSimplesId(){
   return this.dataEntitesSimplesId;
 }
 public void setDataEntitesSimplesId(String dataEntitesSimplesId){
   this.dataEntitesSimplesId=dataEntitesSimplesId;
 }
 public String getEntitesSimplesId(){
   return this.entitesSimplesId;
 }
 public void setEntitesSimplesId(String entitesSimplesId){
   this.entitesSimplesId=entitesSimplesId;
 }
 public int hashCode(){
   int result = 17;
   result = 37 * result + (this.getDataEntitesSimplesId()== null ? 0 : this.getDataEntitesSimplesId().hashCode());
   result = 37 * result + (this.getEntitesSimplesId()== null ? 0 : this.getEntitesSimplesId().hashCode());
  return result;
 }
 public boolean equals(Object other){
  if ((this == other))
   return true;
  if ((other == null))
   return false;
  if (!(other instanceof DataEntitesSimpleBeanId))
   return false;
  DataEntitesSimpleBeanId castOther = (DataEntitesSimpleBeanId) other;
  boolean result=true;
  result=result&&ValueUtils.equals(this.getDataEntitesSimplesId(), castOther.getDataEntitesSimplesId());
  result=result&&ValueUtils.equals(this.getEntitesSimplesId(), castOther.getEntitesSimplesId());
  return result;
 }
}