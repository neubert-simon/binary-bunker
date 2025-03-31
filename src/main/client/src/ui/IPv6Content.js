import styled from "styled-components";

/**
 * The IPv6Content component is a styled div that displays content
 * with different layouts based on the screen size. It includes transitions,
 * animations, and various layout adjustments for responsive design.
 *
 * @component
 * @example
 * <IPv6Content>
 *   <div className="ip-content">
 *     ...
 *   </div>
 * </IPv6Content>
 */
const IPv6Content = styled.div`
    position: relative;
    transform: translateX(-5rem);
    opacity: 0;
    padding: 2rem;
    width: 55%;
    flex: 1;
    background-color: var(--color-black);
    border-radius: var(--border-radius-md);
    
    &::before {
        content: "";
        position: absolute;
        top: 1rem;
        left: -1rem;
        width: 100%;
        height: 100%;
        background-color: transparent;
        pointer-events: none;
        outline: 0.15rem solid var(--color-white);
        border-radius: var(--border-radius-sm);
    }
    
    .ip-content {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
    
    .page-content-vertical,
    .page-content-horizontal {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        font-size: 2.5rem;
        text-align: left;
        color: var(--color-white);
        transition: .5s ease-in-out;
    }
    
    .page-content-vertical {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 3rem;
    }
    
    .page-content-horizontal {
        display: flex;
        align-items: center;
        padding: 0 3rem;
    }
    
    .page-content__values-vertical {
        position: relative;
        display: flex;
        justify-content: start;
        align-items: start;
        flex-direction: column;
        gap: .3rem;
        line-height: 1.5;
    }

    .page-content__values-horizontal {
        position: relative;
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
        padding: 1rem;
    }
    
    .values-horizontal__key {
        font-size: 1.5rem;
    }
    
    .values-horizontal__value {
        display: flex;
        justify-content: start;
        align-items: start;
        flex-direction: column;
    }

    .value-pair {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        gap: 1rem;
        font-size: 1.2rem;
    }

    .value-label {
        font-size: 2.5rem;
    }

    .value-data {
        font-family: monospace;
        font-size: 2rem;
    }

    .binary-row {
        display: block;
        font-family: monospace;
        font-size: 1.1rem;
        word-wrap: break-word; 
    }
    
    .subPagePagination-btn {
        display: none;
    }

    .pageOne {
        top: -100%;
        opacity: 0;
    }
    
    .pageOne.active {
        top: 0;
        opacity: 1;
    }

    .pageTwo {
        top: 100%;
        opacity: 0;
    }
    
    .pageTwo.active {
        top: 0;
        opacity: 1;
    }

    @media (max-width: 576px) {
        transform: translateX(-2rem);
        padding: 1rem;
        width: 100%;
        flex: 1;
        border-radius: var(--border-radius-tiny);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .page-content__values-horizontal {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 80%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            align-items: center;
            gap: 1rem;
            padding: 1rem;
            opacity: 0;
            transform: scale(1.3);
            transition: all .3s ease-in-out;
        }

        .page-content__values-horizontal.active {
            opacity: 1;
            transform: scale(1);
        }

        .values-horizontal__value {
            white-space: nowrap;
        }

        .binary-row {
            word-wrap: normal;
        }

        .value-pair {
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            gap: .3rem;
        }

        .value-label {
            font-size: 1.5rem;
        }

        .value-data {
            font-size: 1rem;
        }

        .subPagePagination-btn {
            position: absolute;
            bottom: 3rem;
            left: 0;
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 3rem;
            z-index: 10;
        }

        .subPageBtn {
            width: 3rem;
            height: 3rem;
            border-radius: var(--border-radius-tiny);
            background-color: var(--color-blue);
            cursor: inherit;
            font-family: impact;
            font-size: 1.2rem;
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 10;
            overflow: hidden;
            transition: all .15s ease-out;
        }
        
        .subPageBtn.active {
            background: linear-gradient(
                    180deg,
                    var(--color-pink-800),
                    var(--color-pink-purple-800),
                    var(--color-purple-800)
            );
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        transform: translateX(-2rem);
        padding: 1rem;
        width: 100%;
        flex: 1;
        border-radius: var(--border-radius-tiny);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .page-content__values-horizontal {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            align-items: center;
            gap: 1rem;
            padding: 1rem;
            opacity: 0;
            transform: scale(1.3);
            transition: all .3s ease-in-out;
        }

        .page-content__values-horizontal.active {
            opacity: 1;
            transform: scale(1);
        }
        
        .values-horizontal__key {
            font-size: 2rem;
        }

        .values-horizontal__value {
            white-space: nowrap;
        }
        
        .binary-row {
            font-size: 1.3rem;
            word-wrap: normal;
        }

        .value-pair {
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            gap: .3rem;
        }

        .value-label {
            font-size: 2rem;
        }

        .value-data {
            font-size: 1.3rem;
        }

        .subPagePagination-btn {
            position: absolute;
            top: 50%;
            left: 0;
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            z-index: 10;
            transform: translateY(-50%);
            padding: 0 3rem;
        }

        .subPageBtn {
            width: 4rem;
            height: 4rem;
            border-radius: var(--border-radius-tiny);
            background-color: var(--color-blue);
            cursor: inherit;
            font-family: impact;
            font-size: 1.5rem;
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 10;
            overflow: hidden;
            transition: all .15s ease-out;
        }

        .subPageBtn.active {
            background: linear-gradient(
                    180deg,
                    var(--color-pink-800),
                    var(--color-pink-purple-800),
                    var(--color-purple-800)
            );
        }
    }

    @media (min-width: 769px) and (max-width: 1599px) {
        transform: translateX(-2rem);
        padding: 1rem;
        flex: 1;
        border-radius: var(--border-radius-md);

        &::before {
            border-radius: var(--border-radius-md);
        }

        .page-content__values-horizontal {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            align-items: center;
            gap: 1rem;
            padding: 1rem;
            opacity: 0;
            transform: scale(1.3);
            transition: all .3s ease-in-out;
        }

        .page-content__values-horizontal.active {
            opacity: 1;
            transform: scale(1);
        }

        .values-horizontal__key {
            font-size: 1.8rem;
        }

        .values-horizontal__value {
            white-space: nowrap;
        }

        .binary-row {
            font-size: 1.2rem;
            word-wrap: normal;
        }

        .value-pair {
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            gap: .3rem;
        }

        .value-label {
            font-size: 1.3rem;
        }

        .value-data {
            font-size: 1.1rem;
        }

        .subPagePagination-btn {
            position: absolute;
            top: 50%;
            left: 0;
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            z-index: 10;
            transform: translateY(-50%);
            padding: 0 3rem;
        }

        .subPageBtn {
            width: 5rem;
            height: 5rem;
            border-radius: var(--border-radius-tiny);
            background-color: var(--color-blue);
            cursor: inherit;
            font-family: impact;
            font-size: 3rem;
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 10;
            overflow: hidden;
            transition: all .15s ease-out;
        }

        .subPageBtn.active {
            background: linear-gradient(
                    180deg,
                    var(--color-pink-800),
                    var(--color-pink-purple-800),
                    var(--color-purple-800)
            );
        }
    }
`;

export default IPv6Content;