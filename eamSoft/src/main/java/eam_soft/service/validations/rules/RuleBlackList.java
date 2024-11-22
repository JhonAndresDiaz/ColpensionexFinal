package eam_soft.service.validations.rules;

import eam_soft.models.Cotizante;
import eam_soft.service.transfer.TransferBlackList;
import eam_soft.service.validations.message.ValidationResult;

public class RuleBlackList implements RuleInterfaz{

    private TransferBlackList transferBlackList = new TransferBlackList();

    @Override
    public ValidationResult validar(Cotizante cotizante) {
        return transferBlackList.searchOnBlacklist(cotizante);
    }
}
