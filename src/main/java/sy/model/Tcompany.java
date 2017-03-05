package sy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tcompany")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tcompany {
	private String id;
	private String name;
	private String principle;
	private String email;
	private String tel;
	private Set<Tuser> tusers = new HashSet<Tuser>(0);
	public Tcompany(){
		
	}
	public Tcompany(String id,String name,String principle,String email,String tel,Set<Tuser> tusers){
		this.id=id;
		this.name=name;
		this.principle=principle;
		this.email=email;
		this.tel=tel;
		this.tusers=tusers;				
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tcompany")
	public Set<Tuser> getTusers() {
		return this.tusers;
	}

	public void setTusers(Set<Tuser> tusers) {
		this.tusers = tusers;
	}

	@Id
	@Column(name = "id", nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", unique = true, nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "principle", unique = false, nullable = true, length = 50)
	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	@Column(name = "email", unique = true, nullable = true, length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "tel", unique = true, nullable = true, length = 100)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
