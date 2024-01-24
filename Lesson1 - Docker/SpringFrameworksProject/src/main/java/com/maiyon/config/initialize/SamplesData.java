package com.maiyon.config.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SamplesData implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private RoleSamples roleSamples;
    @Autowired
    private UserSamples userSamples;
    @Autowired
    private CategorySamples categorySamples;
    @Autowired
    private ProductSamples productSamples;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roleSamples.initRoleSampleData();
        categorySamples.initCategorySampleData();
        productSamples.initProductSampleData();
        userSamples.initUserSampleData();
    }
}
