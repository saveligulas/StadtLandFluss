package gulas.saveli.StadtLandFluss.repo;

public interface ObjectRepository<T> {

    public void store(String token,T t);

    public T retrieve(String token);

    public T search(String name);

    public T delete(String token);
}