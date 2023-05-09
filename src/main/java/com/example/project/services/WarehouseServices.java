package com.example.project.services;
import com.example.project.models.Warehouse;
import com.example.project.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class WarehouseServices {

    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseServices(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

        @Transactional
        public void newWarehouse (Warehouse warehouse){
            warehouseRepository.save(warehouse);
        }

        @Transactional
        public void deleteWarehouse(int id){
            warehouseRepository.deleteById(id);}

        @Transactional
        public Warehouse getWarehouseId(int id){
            Optional<Warehouse> thatWarehouse = warehouseRepository.findById(id);
            return thatWarehouse.orElse(null);
        }

        @Transactional
        public List<Warehouse> getAllWarehouses(){
            return warehouseRepository.findAll();
        }
}
