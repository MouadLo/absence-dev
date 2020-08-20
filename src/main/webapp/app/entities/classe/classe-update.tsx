import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IFiliere } from 'app/shared/model/filiere.model';
import { getEntities as getFilieres } from 'app/entities/filiere/filiere.reducer';
import { getEntity, updateEntity, createEntity, reset } from './classe.reducer';
import { IClasse } from 'app/shared/model/classe.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClasseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClasseUpdate = (props: IClasseUpdateProps) => {
  const [filiereId, setFiliereId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { classeEntity, filieres, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/classe');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getFilieres();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...classeEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="intranetApp.classe.home.createOrEditLabel">
            <Translate contentKey="intranetApp.classe.home.createOrEditLabel">Create or edit a Classe</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : classeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="classe-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="classe-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idClasseLabel" for="classe-idClasse">
                  <Translate contentKey="intranetApp.classe.idClasse">Id Classe</Translate>
                </Label>
                <AvField
                  id="classe-idClasse"
                  type="string"
                  className="form-control"
                  name="idClasse"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomClasseLabel" for="classe-nomClasse">
                  <Translate contentKey="intranetApp.classe.nomClasse">Nom Classe</Translate>
                </Label>
                <AvField
                  id="classe-nomClasse"
                  type="text"
                  name="nomClasse"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="niveauLabel" for="classe-niveau">
                  <Translate contentKey="intranetApp.classe.niveau">Niveau</Translate>
                </Label>
                <AvField
                  id="classe-niveau"
                  type="text"
                  name="niveau"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="classe-filiere">
                  <Translate contentKey="intranetApp.classe.filiere">Filiere</Translate>
                </Label>
                <AvInput id="classe-filiere" type="select" className="form-control" name="filiere.id">
                  <option value="" key="0" />
                  {filieres
                    ? filieres.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.idFiliere}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/classe" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  filieres: storeState.filiere.entities,
  classeEntity: storeState.classe.entity,
  loading: storeState.classe.loading,
  updating: storeState.classe.updating,
  updateSuccess: storeState.classe.updateSuccess,
});

const mapDispatchToProps = {
  getFilieres,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClasseUpdate);
