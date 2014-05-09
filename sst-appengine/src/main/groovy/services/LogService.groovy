package services

import groovyx.gaelyk.GaelykBindingEnhancer
import groovyx.gaelyk.GaelykBindings

import java.util.logging.Logger

//@GaelykBindings
class LogService {

    Logger log = Logger.getLogger(this.class.name)

    def binding

    public LogService(binding){
        this.binding = binding
        binding.log
    }

    void test() {
       // log.info("test() - binding.class: "+binding.class)
       // binding.datastore.hi {}
    }
}
