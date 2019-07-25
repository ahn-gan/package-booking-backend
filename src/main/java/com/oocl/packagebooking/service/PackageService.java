package com.oocl.packagebooking.service;

import com.oocl.packagebooking.model.Package;
import com.oocl.packagebooking.repository.PackageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.oocl.packagebooking.common.Constants.*;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public List<Package> findByConditions(int status, int hasOrdered) {
        return (status == BE_TAKEN_STATUS && hasOrdered == NO_ORDERED) ? packageRepository.findAll() : packageRepository.findAll(new Specification<Package>() {
            @Override
            public Predicate toPredicate(Root<Package> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (status == TO_TAKE_STATUS) {
                    Path statusPath = root.get("status");
                    Predicate p = criteriaBuilder.equal(statusPath, status);
                    predicateList.add(p);
                } else {
                    Path statusPath = root.get("status");
                    Predicate p = criteriaBuilder.isNull(statusPath);
                    predicateList.add(p);
                }
                if (hasOrdered == HAVD_ORDERED) {
                    Path appointmentTime = root.get("appointmentTime");
                    Predicate p = criteriaBuilder.isNotNull(appointmentTime);
                    predicateList.add(p);
                } else {
                    Path appointmentTime = root.get("appointmentTime");
                    Predicate p = criteriaBuilder.isNull(appointmentTime);
                    predicateList.add(p);
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicateList.toArray(predicates);
                criteriaQuery.where(predicates);
                return criteriaBuilder.and(predicates);
            }
        });
    }

    @Transactional
    public Package updatePackage(int id, Package packageInformation) {
        Package originalPackage = packageRepository.findById((long) id).orElse(null);
        if (originalPackage != null) {
            BeanUtils.copyProperties(packageInformation, originalPackage, "id");
            return packageRepository.save(originalPackage);
        }
        return originalPackage;
    }
}
