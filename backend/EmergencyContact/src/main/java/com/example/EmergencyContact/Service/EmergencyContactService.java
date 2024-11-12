package com.example.EmergencyContact.Service;


import com.example.EmergencyContact.Entity.EmergencyContact;
import com.example.EmergencyContact.Repository.EmergencyContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmergencyContactService {

    @Autowired
    private  EmergencyContactRepository repository;

    public List<EmergencyContact> getAllContacts() {
        return repository.findAll();
    }

    public Optional<EmergencyContact> getContactById(Long contactId) {

        return repository.findById(contactId);
    }

    public EmergencyContact addContact(EmergencyContact contact) {
        return repository.save(contact);
    }

    public EmergencyContact updateContact(Long contactId, EmergencyContact contact) {
        if (repository.existsById(contactId)) {
            contact.setContactId(contactId);
            return repository.save(contact);
        }
        return null;
    }

    public void deleteContact(Long contactId) {
        if (repository.existsById(contactId)) {
            repository.deleteById(contactId);
        }
    }

    public String getContactByUserId(long userId) {
         return repository.findByUserId(userId).getContactNumber();
    }
}
