package com.epam.esm.model.dto;

/**
 * Class represents the result of deleting project entity from DB
 */
public class DeleteResultDTO {

    /**
     * Result of Entity deleting
     */
    private boolean result;

    public DeleteResultDTO(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
