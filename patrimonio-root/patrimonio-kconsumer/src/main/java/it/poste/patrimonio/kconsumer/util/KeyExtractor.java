package it.poste.patrimonio.kconsumer.util;

public interface KeyExtractor<TKey, TEvent> 
{
	TKey extractKey(TEvent event);
}
