package dao;

import java.util.List;

public interface DAOFactory<EntityType> {
	
	public void add(EntityType e);
	public List<EntityType> list();
	public EntityType get(Long idEntity);
	public List<EntityType> listByAttribute(String attribute);
	public void delete(Long idEntity);
	public void update(EntityType e);
}
