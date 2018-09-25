package be.vdab.fietsacademy.services;

import java.math.BigDecimal;

public interface DocentService {
	public abstract void opslag(long id, BigDecimal percentage);
}
