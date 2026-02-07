package com.example.springapi.repository;

import com.example.springapi.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserStore
{
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public User save(User user)
    {
        Long id = idGenerator.getAndIncrement();
        user.SetId(id);
        store.put(id,user);
        return user;
    }

    public Optional<User> findById(Long id)
    {
        return Optional.ofNullable(store.get(id));
    }

    public List<User> findAll()
    {
        return new ArrayList<>(store.values());
    }
    public boolean delete(Long id)
    {
        return store.remove(id) != null;
    }


}
