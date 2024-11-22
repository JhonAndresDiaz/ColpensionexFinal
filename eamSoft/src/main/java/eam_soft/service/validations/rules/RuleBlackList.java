package eam_soft.service.validations.rules;

import eam_soft.models.Cotizante;
import eam_soft.service.transfer.TransferirListaNegra;
import eam_soft.service.validations.message.ValidationResult;

public class RuleBlackList implements RuleInterfaz{

    private TransferirListaNegra transferList = new TransferirListaNegra();

    @Override
    public ValidationResult validar(Cotizante cotizante) {
        return transferList.searchOnBlacklist(cotizante);
    }
}
