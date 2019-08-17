(ns amazing-parking-lot.input.processor-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.input.command-argument-option :refer :all])
  (:require [amazing-parking-lot.input.processor :refer [process]]
            [amazing-parking-lot.input.command-argument-option :as option]))

(deftest process-test
  (testing "when the input is valid"
    (testing "it calls the parser, validator, executor and then returns the result"
      (let [parser-called? (atom false)
            validator-called? (atom false)
            expected-result "Success"
            input-parking-lot (atom :parking-lot)
            valid-option (create-valid-option (create-valid-option "some-command" "6") expected-result input-parking-lot)
            executor-called? (atom false)
            input "some-command 6"]
        (with-redefs [amazing-parking-lot.input.parser/parse (fn [_] (reset! parser-called? true)
                                                       valid-option)
                      amazing-parking-lot.command.validator/validate (fn [_] (reset! validator-called? true)
                                                               valid-option)
                      amazing-parking-lot.command.executor/execute (fn [_] (do (reset! executor-called? true)
                                                                       valid-option))]
          (let [result-option (process input nil)]
            (is (= (result result-option) expected-result))
            (is (= (parking-lot result-option) input-parking-lot)))
          (is (true? (and @parser-called? @validator-called? @executor-called?)))))))
  (testing "when the input cannot be parsed"
    (testing "it short circuits after the parser and returns the result"
      (let [validator-called? (atom false)
            executor-called? (atom false)
            expected-result "Input is empty"]
        (with-redefs [amazing-parking-lot.command.validator/validate (fn [_] (reset! validator-called? true))
                      amazing-parking-lot.command.executor/execute (fn [_] (reset! executor-called? true))]
          (is (= (result (process nil nil)) expected-result))
          (is (false? (and @validator-called? @executor-called?))))))))
