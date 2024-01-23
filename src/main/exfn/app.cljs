(ns exfn.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as rf]
            [exfn.subscriptions]
            [exfn.events]
            [exfn.logic :as lgc]
            [goog.string.format]))

;; -- App -------------------------------------------------------------------------

(defn app []
  (let [current-guess        @(rf/subscribe [:current-guess])
        current-guess-number @(rf/subscribe [:current-guess-number])
        guesses              @(rf/subscribe [:guesses])
        clues                @(rf/subscribe [:clues])
        solution             @(rf/subscribe [:solution])
        game-won?            @(rf/subscribe [:game-won?])]
    [:div.container
     [:div.row
      [:div.col.col-lg-8
       [:h1 "Mastermind"]]
      [:div.col.col-lg-4 {:style {:text-align    :right
                                  :padding-right 50}}
       [:i.fab.fa-github]
       [:a {:href  "https://github.com/stuartstein777/peg-solitaire"
            :style {:text-decoration :none}}
        " (repo) "]
       "|"
       [:a {:href  "https://stuartstein777.github.io/"
            :style {:text-decoration :none}}
        " other projects"]]]

     [:div.row
      [:div.col.col-lg-1
       {:style    {:flex-direction  :column
                   :display         :flex
                   :padding         20
                   :align-items     :center
                   :justify-content :center}
        :row-span 8}
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :green) "marble green guessed"
                      :else "marble green")
          :on-click #(rf/dispatch-sync [:add-guess :green])}]]
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :red) "marble red guessed"
                      :else "marble red")
          :on-click #(rf/dispatch-sync [:add-guess :red])}]]
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :blue) "marble blue guessed"
                      :else "marble blue")
          :on-click #(rf/dispatch-sync [:add-guess :blue])}]]
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :orange) "marble orange guessed"
                      :else "marble orange")
          :on-click #(rf/dispatch-sync [:add-guess :orange])}]]
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :yellow) "marble yellow guessed"
                      :else "marble yellow")
          :on-click #(rf/dispatch-sync [:add-guess :yellow])}]]
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :pink) "marble pink guessed"
                      :else "marble pink")
          :on-click #(rf/dispatch-sync [:add-guess :pink])}]]
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :purple) "marble purple guessed"
                      :else "marble purple")
          :on-click #(rf/dispatch-sync [:add-guess :purple])}]]
       [:div.board-cell
        [:div
         {:class    (cond
                      ((set current-guess) :cyan) "marble cyan guessed"
                      :else "marble cyan")
          :on-click #(rf/dispatch-sync [:add-guess :cyan])}]]]

      ;; guesses
      (into
       [:div.col.col-lg-3]
       (into
        (for [j (range 1 9)]
          (into
           [:div.row]
           (for [i (range 1 5)]
             [:div
              {:class (if (< current-guess-number j)
                        "guess-cell inactive"
                        "guess-cell")}
              (if (= current-guess-number j)
                [:div
                 {:class    (when (current-guess i)
                              (str "marble " (name (current-guess i))))
                  :on-click #(rf/dispatch-sync [:remove-guess i])}]
                (let [guess (nth guesses (dec j) nil)]
                  (prn "guess: " guess)
                  (when (and guess (guess i))
                    [:div
                     {:class (str "marble " (name (guess i)))}])))])))))

      ;; checkers
      [:div.col.col-lg-1
       (for [i (range 1 9)]
         [:div.row
          [:div.check-guess-holder
           {:style {:display (if (= 4 (->> current-guess vals (remove nil?) count))
                                :block
                                :none)}
            :on-click #(rf/dispatch-sync [:check-guess])}
           (when (and (not game-won?) (= current-guess-number i))
             [:i.fas.fa-play.check-guess])]])]

      ;; clues
      [:div.col.col-lg-2
       (for [i (range 0 9)]
         [:div.row
          (if (> (count clues) i)
            (let [[a b c d] (nth clues i nil)]
              [:div.clue.clue-grid
               [:div (lgc/get-clue-marker a)]
               [:div (lgc/get-clue-marker b)]
               [:div (lgc/get-clue-marker c)]
               [:div (lgc/get-clue-marker d)]])
            [:div])])]]

     [:div.row
      [:div.col.col-lg-1]
      [:div.col.col-lg-3
       (when (or (= current-guess-number 9) game-won?)
         [:div
          [:div.row
           {:style {:margin-left 70}}
           [:h3 (if game-won? "Winner!" "Solution")]]
          (into
           [:div.row]
           (for [i (range 0 4)]
             [:div.guess-cell
              [:div
               {:class (str "marble " (name (nth solution i)))}]]))])]]
     [:div.row
      [:div.col.col-lg-1]
      [:div.col.col-lg-3
       [:button.btn.btn-primary
        {:style {:margin-top 20}
         :on-click #(rf/dispatch-sync [:initialize])}
        "New Game"]]]]))

;; -- After-Load --------------------------------------------------------------------
;; Do this after the page has loaded.
;; Initialize the initial db state.
(defn ^:dev/after-load start
  []
  (dom/render [app]
              (.getElementById js/document "app")))

(defn ^:export init []
  (start))

; dispatch the event which will create the initial state. 
(defonce initialize (rf/dispatch-sync [:initialize]))

(comment


  

  



  )