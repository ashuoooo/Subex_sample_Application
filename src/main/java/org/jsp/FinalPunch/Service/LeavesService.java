package org.jsp.FinalPunch.Service;

import org.jsp.FinalPunch.Service.ServiceImp.LeavesServiceImpl;
import org.jsp.FinalPunch.Entity.Leaves;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class LeavesService {

    @Autowired
    private LeavesServiceImpl leavesSearviceImp;

    public List<Leaves> findallEmployeeLeaves() {
        return leavesSearviceImp.findallEmployeeDaoLeaves();
    }
    public Leaves saveEmployeeLeaves(Leaves leaves) {
        return leavesSearviceImp.saveEmployeeLeaves(leaves);
    }

    public Optional<Leaves> findEmployeeLeavesById(int id) {
        return leavesSearviceImp.findEmployeeLeavesbYId(id);
    }

    public Optional<Leaves> deleteEmployeeLeavesId(int id) {
        return leavesSearviceImp.deleteEmployeeLeavesById(id);
    }

    public Optional<Leaves> updateEmployeeLeavesById(int id, Leaves updateLeavesdetails) {
        return leavesSearviceImp.updateEmployeeLeaves(id,updateLeavesdetails);
    }
}
